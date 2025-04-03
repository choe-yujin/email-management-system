package com.metaverse.mail.service.impl.mail;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dto.mail.EmailComposeDto;
import com.metaverse.mail.dto.mail.EmailSearchDto;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.model.Email;
import com.metaverse.mail.model.EmailLink;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.EmailService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EmailServiceImpl implements EmailService {

    private final EmailDao emailDao;
    private final EmailLinkDao emailLinkDao;
    private final UserDao userDao;

    /**
     * 생성자
     *
     * @param emailDao 이메일 DAO
     * @param emailLinkDao 이메일 링크 DAO
     * @param userDao 사용자 DAO
     */
    public EmailServiceImpl(EmailDao emailDao, EmailLinkDao emailLinkDao, UserDao userDao) {
        this.emailDao = emailDao;
        this.emailLinkDao = emailLinkDao;
        this.userDao = userDao;
    }

    /**
     * 이메일 발송
     *
     * 새로운 이메일을 작성하고 지정된 수신자들에게 발송합니다.
     * 이메일 작성 화면에서 발송 버튼을 클릭할 때 호출됩니다.
     *
     * 버그 수정: 모든 수신자가 유효해야만 이메일을 전송하도록 수정
     *
     * @param emailDto 이메일 작성 정보(수신자, 제목, 내용 등)
     * @param senderId 발신자 ID
     * @return 발송 성공 여부(true: 성공, false: 실패) 및 결과 메시지
     */
    @Override
    public Map<String, Object> sendEmail(EmailComposeDto emailDto, int senderId) {
        // 결과를 담을 Map 객체 생성
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", "");

        // 1. 수신자 유효성 검사
        List<User> validReceivers = new ArrayList<>();
        List<String> invalidReceivers = new ArrayList<>();

        for (String receiverEmail : emailDto.getReceiverEmails()) {
            User receiver = userDao.findByEmailId(receiverEmail);

            // 수신자가 존재하고 활성 상태인 경우에만 추가
            if (receiver != null && receiver.getStatus() != 'D') {
                validReceivers.add(receiver);
            } else {
                invalidReceivers.add(receiverEmail);
            }
        }

        // 2. 유효하지 않은 수신자가 있는 경우 전송 실패로 처리
        if (!invalidReceivers.isEmpty()) {
            StringBuilder message = new StringBuilder("다음 수신자가 유효하지 않습니다: ");
            message.append(String.join(", ", invalidReceivers));
            message.append("\n메일 전송이 취소되었습니다.");

            result.put("message", message.toString());
            return result;
        }

        // 3. 유효한 수신자가 없는 경우 전송 실패로 처리
        if (validReceivers.isEmpty()) {
            result.put("message", "메일을 받을 수 있는 수신자가 없습니다.");
            return result;
        }

        // 4. 이메일 및 이메일 링크 생성
        Connection conn = null;
        try {
            conn = JDBCConnection.getConnection();
            conn.setAutoCommit(false);

            // 이메일 객체 생성
            Email email = new Email();
            email.setSenderId(senderId);
            email.setTitle(emailDto.getTitle());
            email.setBody(emailDto.getBody());
            email.setStatus('Y');
            email.setCreatedAt(LocalDateTime.now());

            // 이메일 저장
            int emailId = emailDao.createEmail(email);
            if (emailId <= 0) {
                conn.rollback();
                result.put("message", "이메일 생성 중 오류가 발생했습니다.");
                return result;
            }

            // 유효한 수신자에게 이메일 링크 생성
            for (User receiver : validReceivers) {
                boolean linked = emailLinkDao.createEmailLink(emailId, receiver.getIdx());
                if (!linked) {
                    conn.rollback();
                    result.put("message", "이메일 링크 생성 중 오류가 발생했습니다.");
                    return result;
                }
            }

            conn.commit();
            result.put("success", true);
            result.put("message", "메일 전송 완료! " + validReceivers.size() + "명의 수신자에게 메일을 보냈습니다.");
            return result;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            result.put("message", "데이터베이스 오류: " + e.getMessage());
            return result;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 받은 이메일 목록 조회
     *
     * 특정 사용자가 받은 모든 이메일의 목록을 조회합니다.
     * 삭제되지 않은 이메일만 포함되며, 최신 이메일부터 표시됩니다.
     *
     * 주요 처리 내용:
     * 사용자 ID 유효성 검증
     * 받은 이메일 목록 조회
     * 발신자 정보 추가
     * DTO 변환 및 정렬
     *
     * @param userId 사용자 ID
     * @return 받은 이메일 목록
     */
    @Override
    public List<ReceivedEmailDto> getReceivedEmails(int userId) {
        List<EmailLink> emailLinks = emailLinkDao.getLinksByReceiverId(userId)
                .stream()
                .filter(link -> link.getIsDeleted() == 'N')
                .toList(); // 반환된 리스트를 수정할 계획이 있다면 collect으로, 없다면 toList()로

        List<ReceivedEmailDto> receivedEmails = new ArrayList<>();
        for (EmailLink link : emailLinks) {
            Email email = emailDao.getEmailById(link.getEmailIdx());
            if (email == null) continue;

            User sender = userDao.findById(email.getSenderId());
            if (sender == null) continue;

            // 생성자를 직접 사용하여 DTO 생성
            ReceivedEmailDto emailDto = new ReceivedEmailDto(
                    email.getEmailIdx(),
                    sender.getNickname(),
                    sender.getEmailId(),
                    email.getTitle(),
                    email.getBody(),
                    link.getIsReaded() == 'Y',
                    email.getCreatedAt()
            );

            receivedEmails.add(emailDto);
        }

        return receivedEmails.stream()
                .sorted(Comparator.comparing(ReceivedEmailDto::getSentDate).reversed())
                .collect(Collectors.toList());
    }


    /**
     * 이메일 상세 정보 조회
     *
     * 특정 이메일의 상세 내용을 조회합니다.
     * 이메일을 읽을 때 호출되며, 읽음 상태로 자동 변경됩니다.
     *
     * 주요 처리 내용:
     * 이메일 존재 여부 확인
     * 사용자 권한 확인(이메일 수신자 또는 발신자인지)
     * 읽음 상태로 변경
     * 상세 정보 DTO 변환
     *
     * @param emailId 이메일 ID
     * @param userId  사용자 ID
     * @return 이메일 상세 정보
     */
    /**
     * 이메일 상세 정보 조회
     *
     * 특정 이메일의 상세 내용을 조회합니다.
     * 이메일을 읽을 때 호출되며, 읽음 상태로 자동 변경됩니다.
     *
     * 주요 처리 내용:
     * 이메일 존재 여부 확인
     * 사용자 권한 확인(이메일 수신자 또는 발신자인지)
     * 읽음 상태로 변경
     * 상세 정보 DTO 변환
     *
     * @param emailId 이메일 ID
     * @param userId 사용자 ID
     * @return 이메일 상세 정보
     */
    @Override
    public ReceivedEmailDto getEmailDetails(int emailId, int userId) {
        // 이메일 정보 조회
        Email email = emailDao.getEmailById(emailId);

        if (email == null) {
            System.err.println("이메일을 찾을 수 없습니다: " + emailId);
            return null;
        }

        // 사용자가 해당 이메일의 수신자인지 확인
        List<EmailLink> links = emailLinkDao.getLinksByReceiverId(userId)
                .stream()
                .filter(link -> link.getEmailIdx() == emailId)
                .collect(Collectors.toList()); // 반환된 리스트를 수정할 계획이 있다면 collect 으로, 없다면 toList()로

        if (links.isEmpty()) {
            System.err.println("사용자가 해당 이메일의 수신자가 아닙니다.");
            return null;
        }

        EmailLink emailLink = links.get(0);

        // 이메일 읽음 상태로 변경 (아직 읽지 않은 경우)
        if (emailLink.getIsReaded() == 'N') {
            emailLinkDao.markAsRead(emailLink.getLinkIdx());
        }

        // 발신자 정보 조회
        User sender = userDao.findById(email.getSenderId());

        if (sender == null) {
            System.err.println("발신자 정보를 찾을 수 없습니다.");
            return null;
        }

        // DTO 변환 및 반환
        return new ReceivedEmailDto(
                email.getEmailIdx(),
                sender.getNickname(),
                sender.getEmailId(),
                email.getTitle(),
                email.getBody(),
                true, // 읽음 상태로 처리됨
                email.getCreatedAt()
        );
    }

    /**
     * 키워드로 이메일 검색
     *
     * 제목이나 내용에 특정 키워드가 포함된 이메일을 검색합니다.
     * 발신 및 수신 이메일 모두에서 검색이 수행됩니다.
     *
     * 주요 처리 내용:
     * 검색 키워드 유효성 검사
     * 제목/내용 기반 검색
     * 발신/수신 이메일 통합
     * 검색 결과 DTO 변환 및 정렬
     *
     * @param keyword 검색 키워드
     * @param userId  사용자 ID
     * @return 검색된 이메일 목록
     */
    @Override
    public List<EmailSearchDto> searchEmails(String keyword, int userId) {
        // 입력값 유효성 검사
        if (keyword == null || keyword.trim().isEmpty() || userId <= 0) {
            return new ArrayList<>();
        }

        // 이메일 검색
        List<Email> emails = emailDao.searchEmails(keyword, userId);

        // 검색 결과 변환
        List<EmailSearchDto> results = new ArrayList<>();

        for (Email email : emails) {
            // 이메일에 대한 추가 정보 조회
            String personName = "";
            String emailType = "";
            boolean isRead = false;

            // ResultSet에서 추가 정보를 가져올 수 있어야 하지만,
            // 현재 구현에서는 이메일 객체에 이 정보가 없으므로 별도로 조회 필요
            // 실제 구현에서는 DAO에서 이 정보를 함께 반환하도록 수정하는 것이 좋음

            // 임시 구현 - ResultSet에서 이 정보를 함께 가져오도록 수정해야 함
            User sender = userDao.findById(email.getSenderId());

            if (sender != null) {
                personName = sender.getNickname();
            }

            // 발신/수신 여부와 읽음 상태 확인 로직
            List<EmailLink> links = emailLinkDao.getLinksByReceiverId(userId)
                    .stream()
                    .filter(link -> link.getEmailIdx() == email.getEmailIdx())
                    .collect(Collectors.toList());

            if (!links.isEmpty()) {
                // 받은 메일인 경우
                emailType = "수신함";
                isRead = links.get(0).getIsReaded() == 'Y';
            } else {
                // 보낸 메일인 경우
                emailType = "발신함";
                isRead = true; // 보낸 메일은 항상 읽음 상태
            }

            // DTO 생성 및 추가
            EmailSearchDto dto = new EmailSearchDto(
                    email.getEmailIdx(),
                    email.getTitle(),
                    personName,
                    emailType,
                    isRead,
                    email.getCreatedAt()
            );

            results.add(dto);
        }

        // 최신 메일 기준으로 정렬
        return results.stream()
                .sorted(Comparator.comparing(EmailSearchDto::getSentDate).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 이메일에 답장
     *
     * 받은 이메일에 대한 답장을 보냅니다.
     * 원본 이메일 정보를 참조하여 답장을 구성합니다.
     *
     * 주요 처리 내용:
     * 원본 이메일 존재 여부 확인
     * 원본 발신자를 수신자로 설정
     * 제목에 'Re:' 접두어 추가
     * 새 이메일 저장 및 발송
     *
     * @param originalEmailId 원본 이메일 ID
     * @param replyContent    답장 내용
     * @param senderId        발신자 ID
     * @return 답장 발송 성공 여부(true: 성공, false: 실패)
     */
    @Override
    public boolean replyToEmail(int originalEmailId, String replyContent, int senderId) {
        // 원본 이메일 존재 여부 확인
        Email originalEmail = emailDao.getEmailById(originalEmailId);
        if (originalEmail == null) {
            System.err.println("원본 이메일을 찾을 수 없습니다: " + originalEmailId);
            return false;
        }

        // 원본 이메일의 발신자 정보 조회
        User originalSender = userDao.findById(originalEmail.getSenderId());
        if (originalSender == null) {
            System.err.println("원본 발신자 정보를 찾을 수 없습니다.");
            return false;
        }

        // 제목 설정 (이미 Re: 가 포함되어 있지 않은 경우에만 추가)
        String replyTitle = originalEmail.getTitle();
        if (!replyTitle.startsWith("Re:")) {
            replyTitle = "Re: " + replyTitle;
        }

        // 이메일 작성 DTO 생성
        List<String> receiverEmails = Arrays.asList(originalSender.getEmailId());
        EmailComposeDto replyDto = new EmailComposeDto(receiverEmails, replyTitle, replyContent);

        // 답장 이메일 발송
        Map<String, Object> result = sendEmail(replyDto, senderId);
        return (boolean) result.get("success");
    }
}
