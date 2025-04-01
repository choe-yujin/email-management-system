package com.metaverse.mail.view.impl.mail;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Constants;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.mail.EmailComposeDto;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.view.interfaces.mail.ComposeView;

/**
 * 메일 작성 화면 구현 클래스
 *
 * ComposeView 인터페이스를 구현하여 이메일 작성 및 전송 기능을 제공합니다.
 *
 * @author 유진
 * @version 1.0
 */
public class ComposeViewImpl implements ComposeView {

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
    public ComposeViewImpl(Scanner scanner, EmailService emailService) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.emailService = emailService;
        this.session = Session.getInstance();
    }

    @Override
    public void showComposeForm() {
        consoleHelper.displayHeader("✉️ 메일 작성");

        // 수신자 이메일 입력 받기
        String receiversInput = consoleHelper.getStringInput("→ 받는 사람 이메일(여러 명에게 보낼 경우 쉼표로 구분): ");
        List<String> receiverEmails = parseReceiverEmails(receiversInput);

        // 제목 입력 받기
        String title = consoleHelper.getStringInput("→ 제목: ");

        // 내용 입력 받기
        String body = consoleHelper.getStringInput("→ 내용: ");

        // 입력값으로 EmailComposeDto 생성
        EmailComposeDto emailDto = new EmailComposeDto(receiverEmails, title, body);

        // 현재 로그인한 사용자 ID 가져오기
        int senderId = session.getCurrentUserId();

        // 메일 전송 요청
        consoleHelper.displayDivider();

        boolean success = emailService.sendEmail(emailDto, senderId);

        // 전송 결과 표시
        if (success) {
            System.out.println("→ 메일 전송 완료! " + receiverEmails.size() + "명의 수신자에게 메일을 보냈습니다.");
        } else {
            System.out.println("→ 메일 전송 실패! 다시 시도해주세요.");
        }

        System.out.println("→ 메인 메뉴로 이동합니다.");
    }

    /**
     * 수신자 이메일 문자열을 파싱하여 리스트로 변환
     *
     * @param receiversInput 콤마로 구분된 이메일 주소 문자열
     * @return 수신자 이메일 주소 리스트
     */
    private List<String> parseReceiverEmails(String receiversInput) {
        return Arrays.stream(receiversInput.split(","))
                .map(String::trim)
                .filter(email -> !email.isEmpty())
                .map(this::ensureEmailDomain)
                .collect(Collectors.toList());
    }

    /**
     * 이메일 주소에 도메인이 없는 경우 기본 도메인을 추가
     *
     * @param email 확인할 이메일 주소
     * @return 도메인이 포함된 이메일 주소
     */
    private String ensureEmailDomain(String email) {
        // 이미 @ 기호가 포함된 경우 그대로 반환
        if (email.contains("@")) {
            return email;
        }

        // 도메인이 없는 경우 기본 도메인 추가
        return Constants.toEmail(email);
    }
}