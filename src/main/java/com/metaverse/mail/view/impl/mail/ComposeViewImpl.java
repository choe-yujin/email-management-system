package com.metaverse.mail.view.impl.mail;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.mail.EmailComposeDto;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.view.interfaces.mail.ComposeView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ComposeViewImpl implements ComposeView {

    private final Scanner scanner;
    private final ConsoleHelper consoleHelper;
    private final EmailService emailService;

    public ComposeViewImpl(Scanner scanner, ConsoleHelper consoleHelper, EmailService emailService) {
        this.scanner = scanner;
        this.consoleHelper = consoleHelper;
        this.emailService = emailService;
    }

    @Override
    public void showComposeForm() {
        consoleHelper.displayHeader("✉️ 메일 작성");

        // 1. 수신자 입력 받기
        String receiversInput = consoleHelper.getStringInput("→ 받는 사람 이메일(여러 명에게 보낼 경우 쉼표로 구분): ");
        List<String> receiverEmails = parseReceiverEmails(receiversInput);

        // 수신자 입력 검증
        if (receiverEmails.isEmpty()) {
            System.out.println("→ 유효한 수신자가 입력되지 않았습니다.");
            consoleHelper.displayDivider();
            return;
        }

        // 2. 제목 입력 받기
        String title = consoleHelper.getStringInput("→ 제목: ");

        // 제목 입력 검증
        if (title.trim().isEmpty()) {
            System.out.println("→ 제목은 필수 입력 항목입니다.");
            consoleHelper.displayDivider();
            return;
        }

        // 3. 내용 입력 받기
        String body = consoleHelper.getStringInput("→ 내용: ");

        // 4. 이메일 작성 DTO 생성
        EmailComposeDto emailDto = new EmailComposeDto(receiverEmails, title, body);

        // 5. 이메일 전송
        int currentUserId = Session.getInstance().getCurrentUserId();
        Map<String, Object> result = emailService.sendEmail(emailDto, currentUserId);

        consoleHelper.displayDivider();

        // 6. 결과 표시
        System.out.println(result.get("message"));
        System.out.println("→ 메인 메뉴로 이동합니다.");
    }

    /**
     * 수신자 이메일 문자열을 파싱하여 목록으로 변환
     *
     * @param receiversInput 콤마로 구분된 수신자 이메일 문자열
     * @return 수신자 이메일 목록
     */
    private List<String> parseReceiverEmails(String receiversInput) {
        return Arrays.stream(receiversInput.split(","))
                .map(String::trim)
                .filter(email -> !email.isEmpty())
                .collect(Collectors.toList());
    }
}