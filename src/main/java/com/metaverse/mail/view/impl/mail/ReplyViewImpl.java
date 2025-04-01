package com.metaverse.mail.view.impl.mail;

import java.util.Scanner;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.view.interfaces.mail.ReplyView;

/**
 * 메일 답장 화면 구현 클래스
 *
 * @author 유진
 * @version 1.0
 */
public class ReplyViewImpl implements ReplyView {

    /** 사용자 입력을 처리하는 Scanner 객체 */
    private Scanner scanner;

    /** 콘솔 UI를 관리하는 헬퍼 */
    private ConsoleHelper consoleHelper;

    /** 이메일 서비스 */
    private EmailService emailService;

    /** 사용자 세션 */
    private Session session;

    /**
     * 생성자
     *
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     * @param emailService 이메일 서비스
     */
    public ReplyViewImpl(Scanner scanner, EmailService emailService) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.emailService = emailService;
        this.session = Session.getInstance();
    }

    /**
     * 답장 작성 화면 표시
     *
     * @param originalEmail 원본 이메일 정보
     */
    @Override
    public void showReplyForm(ReceivedEmailDto originalEmail) {
        consoleHelper.displayHeader("✉️ 답장 작성");

        // 원본 이메일 정보 표시
        System.out.println("(받는 사람 이메일): " + originalEmail.getSenderEmail());
        System.out.println("제목: Re: " + originalEmail.getTitle());

        // 내용 입력 받기
        String replyContent = consoleHelper.getStringInput("내용: ");

        // 현재 로그인한 사용자 ID 가져오기
        int senderId = session.getCurrentUserId();

        // 답장 전송 요청
        consoleHelper.displayDivider();

        boolean success = emailService.replyToEmail(
                originalEmail.getEmailId(),
                replyContent,
                senderId);

        // 전송 결과 표시
        if (success) {
            System.out.println("→ 답장 전송 완료! " + originalEmail.getSenderName() + "(" +
                    originalEmail.getSenderEmail() + ")에게 답장을 보냈습니다.");
        } else {
            System.out.println("→ 답장 전송 실패! 다시 시도해주세요.");
        }

        System.out.println("→ 메인 메뉴로 이동합니다.");
    }
}