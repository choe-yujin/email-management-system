package com.metaverse.mail.service.impl.mail;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dto.mail.EmailComposeDto;
import com.metaverse.mail.dto.mail.EmailSearchDto;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.model.Email;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.EmailService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
     * 주요 처리 내용:
     * 이메일 내용 유효성 검사
     * 수신자 존재 여부 확인
     * 이메일 데이터 저장
     * 수신자별 이메일 링크 생성
     *
     * @param emailDto 이메일 작성 정보(수신자, 제목, 내용 등)
     * @param senderId 발신자 ID
     * @return 발송 성공 여부(true: 성공, false: 실패)
     */
    @Override
    public boolean sendEmail(EmailComposeDto emailDto, int senderId) {
        Connection conn = null;
        boolean autoCommit = false;
        boolean success = false;

        try {
            // 입력값 유효성 검사
            if (emailDto.getReceiverEmails() == null || emailDto.getReceiverEmails().isEmpty() ||
                    emailDto.getTitle() == null || emailDto.getTitle().trim().isEmpty() ||
                    emailDto.getBody() == null) {
                return false;
            }

            // 트랜잭션 시작을 위한 연결 획득
            try {
                conn = JDBCConnection.getConnection();
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                System.err.println("데이터베이스 연결 실패: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

            // 이메일 객체 생성
            Email email = new Email();
            email.setSenderId(senderId);
            email.setTitle(emailDto.getTitle());
            email.setBody(emailDto.getBody());
            email.setStatus('Y'); // 발송 완료 상태
            email.setCreatedAt(LocalDateTime.now());

            // 이메일 저장
            int emailId = emailDao.createEmail(email);

            if (emailId <= 0) {
                // 이메일 생성 실패
                System.err.println("이메일 생성 실패");
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException e) {
                    System.err.println("롤백 실패: " + e.getMessage());
                    e.printStackTrace();
                }
                return false;
            }

            // 각 수신자에 대해 처리
            List<String> receiverEmails = emailDto.getReceiverEmails();
            for (String receiverEmail : receiverEmails) {
                // 수신자 존재 여부 확인
                User receiver = userDao.findByEmailId(receiverEmail);

                if (receiver == null) {
                    // 존재하지 않는 수신자일 경우 롤백
                    System.err.println("존재하지 않는 수신자: " + receiverEmail);
                    try {
                        conn.rollback();
                    } catch (SQLException e) {
                        System.err.println("롤백 실패: " + e.getMessage());
                        e.printStackTrace();
                    }
                    return false;
                }

                // 이메일과 수신자 연결
                boolean linked = emailLinkDao.createEmailLink(emailId, receiver.getIdx());

                if (!linked) {
                    // 연결 실패 시 롤백
                    System.err.println("이메일 링크 생성 실패: " + receiverEmail);
                    try {
                        conn.rollback();
                    } catch (SQLException e) {
                        System.err.println("롤백 실패: " + e.getMessage());
                        e.printStackTrace();
                    }
                    return false;
                }
            }

            // 모든 작업 성공 시 커밋
            try {
                conn.commit();
                success = true;
            } catch (SQLException e) {
                System.err.println("커밋 실패: " + e.getMessage());
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("롤백 실패: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
            }

        } catch (Exception e) {
            // 모든 예외 처리
            System.err.println("이메일 발송 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("롤백 실패: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
        } finally {
            // 원래 autoCommit 설정 복원
            try {
                if (conn != null) {
                    conn.setAutoCommit(autoCommit);
                }
            } catch (SQLException e) {
                System.err.println("autoCommit 설정 복원 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return success;
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
        return List.of();
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
    @Override
    public ReceivedEmailDto getEmailDetails(int emailId, int userId) {
        return null;
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
        return List.of();
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
     * 원본 이메일 인용 추가
     * 새 이메일 저장 및 발송
     *
     * @param originalEmailId 원본 이메일 ID
     * @param replyContent    답장 내용
     * @param senderId        발신자 ID
     * @return 답장 발송 성공 여부(true: 성공, false: 실패)
     */
    @Override
    public boolean replyToEmail(int originalEmailId, String replyContent, int senderId) {
        return false;
    }
}
