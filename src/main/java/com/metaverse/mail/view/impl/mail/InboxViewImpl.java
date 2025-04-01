package com.metaverse.mail.view.impl.mail;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.service.interfaces.InboxService;
import com.metaverse.mail.view.interfaces.mail.InboxView;
import com.metaverse.mail.view.interfaces.mail.ReplyView;

/**
 * 받은 메일함 화면 구현 클래스
 *
 * @author 유진
 * @version 1.0
 */
public class InboxViewImpl implements InboxView {

    /** 사용자 입력을 처리하는 Scanner 객체 */
    private Scanner scanner;

    /** 콘솔 UI를 관리하는 헬퍼 */
    private ConsoleHelper consoleHelper;

    /** 이메일 서비스 */
    private EmailService emailService;

    /** 받은 메일함 관련 서비스 */
    private InboxService inboxService;
    
    /** 답장 화면 */
    private ReplyView replyView;

    /** 사용자 세션 */
    private Session session;

    /** 날짜 포맷팅을 위한 DateTimeFormatter */
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 생성자
     *
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     * @param emailService 이메일 서비스
     * @param inboxService 받은 메일함 관련 서비스
     */
    public InboxViewImpl(Scanner scanner, EmailService emailService, InboxService inboxService) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.emailService = emailService;
        this.inboxService = inboxService;
        this.replyView = new ReplyViewImpl(scanner, emailService);
        this.session = Session.getInstance();
    }

    /**
     * 받은 메일함 표시
     */
    @Override
    public void showInbox() {
        // 받은 메일함 헤더 표시
        consoleHelper.displayHeader("📥 받은 메일함");

        // 현재 로그인한 사용자의 받은 메일 목록 조회
        int userId = session.getCurrentUserId();
        List<ReceivedEmailDto> emails = emailService.getReceivedEmails(userId);

        // 메일 목록 표시
        showEmailList(emails);

        // 사용자 입력 처리
        int choice = consoleHelper.getIntInput("조회할 메일 번호 입력 (0: 뒤로 가기): ", 0, emails.size());

        if (choice == 0) {
            return; // 메인 메뉴로 돌아가기
        } else {
            // 선택한 이메일의 ID 확인
            int emailId = emails.get(choice - 1).getEmailId();

            // 선택한 이메일 상세 보기
            ReceivedEmailDto emailDetail = emailService.getEmailDetails(emailId, userId);

            if (emailDetail != null) {
                showEmailDetail(emailDetail);
            } else {
                System.out.println("→ 이메일을 조회할 수 없습니다.");
                consoleHelper.displayDivider();
            }
        }
    }

    /**
     * 메일 목록 표시
     *
     * @param emails 메일 목록
     */
    @Override
    public void showEmailList(List<ReceivedEmailDto> emails) {
        if (emails == null || emails.isEmpty()) {
            System.out.println("→ 받은 메일이 없습니다.");
            consoleHelper.displayDivider();
            return;
        }

        for (int i = 0; i < emails.size(); i++) {
            ReceivedEmailDto email = emails.get(i);
            String readStatus = email.isRead() ? "[읽음]" : "[미확인]";
            String dateStr = email.getSentDate().format(dateFormatter);

            System.out.printf("%d. %s %s - \"%s\" (%s)\n",
                    i + 1, readStatus, email.getSenderName(),
                    email.getTitle(), dateStr);
        }

        consoleHelper.displayDivider();
    }

    /**
     * 메일 상세 내용 표시
     *
     * @param email 이메일 DTO
     */
    @Override
    public void showEmailDetail(ReceivedEmailDto email) {
        consoleHelper.displayHeader("📩 메일 상세 보기");

        System.out.println("보낸 사람: " + email.getSenderName() + " (" + email.getSenderEmail() + ")");
        System.out.println("제목: " + email.getTitle());
        System.out.println("내용: " + email.getBody());
        System.out.println("보낸 날짜: " + email.getFormattedDate());

        consoleHelper.displayDivider();

        System.out.println("1. 답장하기");
        System.out.println("2. 삭제하기");
        System.out.println("3. 메일함으로 돌아가기");

        int choice = consoleHelper.getIntInput("선택 (1-3): ", 1, 3);

        processEmailDetailOption(email, choice);
    }

    /**
     * 메일 상세 옵션 처리
     *
     * @param email 이메일 객체
     * @param choice 사용자 선택
     */
    private void processEmailDetailOption(ReceivedEmailDto email, int choice) {
        switch (choice) {
            case 1:
                // 답장 화면으로 이동
                replyView.showReplyForm(email);
                break;
            case 2:
                // 이메일 삭제 기능 구현
                deleteEmail(email.getEmailId());
                break;
            case 3:
                // 메일함으로 돌아가기
                showInbox();
                break;
        }
    }

    /**
     * 이메일 삭제 처리
     *
     * @param emailId 삭제할 이메일 ID
     */
    private void deleteEmail(int emailId) {
        // 삭제 확인 메시지
        boolean confirm = consoleHelper.getConfirmation("정말로 이 메일을 삭제하시겠습니까?");

        if (!confirm) {
            System.out.println("→ 삭제가 취소되었습니다.");
            showInbox();
            return;
        }

        // 현재 로그인한 사용자의 ID 가져오기
        int userId = session.getCurrentUserId();

        // 이메일 삭제 요청 - 주입받은 inboxService 사용
        boolean success = inboxService.deleteReceivedEmail(emailId, userId);

        if (success) {
            System.out.println("→ 메일이 성공적으로 삭제되었습니다. 휴지통에서 확인할 수 있습니다.");
        } else {
            System.out.println("→ 메일 삭제에 실패했습니다.");
        }

        // 메일함으로 돌아가기
        showInbox();
    }

    /**
     * 이메일 읽음 표시 결과
     *
     * @param emailId 이메일 ID
     * @param success 성공 여부
     */
    @Override
    public void showMarkAsReadResult(long emailId, boolean success) {
        if (success) {
            System.out.println("→ 메일이 읽음 상태로 표시되었습니다.");
        } else {
            System.out.println("→ 메일 상태 변경에 실패했습니다.");
        }
    }
}
