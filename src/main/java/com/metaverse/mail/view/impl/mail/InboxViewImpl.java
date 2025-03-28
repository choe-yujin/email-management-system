package com.metaverse.mail.view.impl.mail;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.view.interfaces.mail.InboxView;

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

    /** 사용자 세션 */
    private Session session;

    /** 날짜 포맷팅을 위한 DateTimeFormatter */
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 생성자
     *
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     * @param emailService 이메일 서비스
     */
    public InboxViewImpl(Scanner scanner, EmailService emailService) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.emailService = emailService;
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

            // 나중에 상세 보기 기능 구현 시 사용할 부분
            // TODO: 이메일 상세 보기 기능 구현
            System.out.println("선택된 이메일 ID: " + emailId);
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

    }

    /**
     * 이메일 읽음 표시 결과
     *
     * @param emailId 이메일 ID
     * @param success 성공 여부
     */
    @Override
    public void showMarkAsReadResult(long emailId, boolean success) {

    }
}