package com.metaverse.mail.view.impl.user;

import com.metaverse.mail.common.Constants;
import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.dto.user.UserRegisterDto;
import com.metaverse.mail.service.interfaces.UserService;
import com.metaverse.mail.view.interfaces.user.RegisterView;

import java.util.Scanner;

public class RegisterViewImpl implements RegisterView {
    private final UserService userService; // UserService 객체: 회원가입 검증을 담당
    private final ConsoleHelper consoleHelper; // ConsoleHelper 객체: 콘솔 입출력을 담당
    private final Scanner scanner; // Scanner 객체 추가

    // RegisterView 생성자: ConsoleHelper와 UserService 객체를 주입받아 초기화
    public RegisterViewImpl(ConsoleHelper consoleHelper, UserService userService) {
        this.consoleHelper = consoleHelper;
        this.userService = userService;
        this.scanner = new Scanner(System.in); // Scanner 초기화 추가
    }

    @Override
    public void showRegistrationForm() {
        while (true) {
            // 회원가입 화면 제목 출력
            consoleHelper.displayHeader("📝 회원가입");

            // 사용자로부터 아이디, 비밀번호, 닉네임을 입력 받음
            String userId = consoleHelper.getStringInput("아이디: ");
            String emailId = Constants.toEmail(userId); // userId를 이메일 형식으로 변환
            String password = consoleHelper.getPasswordInput("비밀번호: ");
            String nickname = consoleHelper.getStringInput("닉네임: ");
            consoleHelper.displayDivider(); // 구분선 출력

            // 아이디, 비밀번호, 닉네임이 비어있으면 다시 입력 받도록 함
            if (emailId.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
                System.out.println("→ 아이디, 비밀번호, 닉네임을 모두 입력하세요.");
                continue; // 다시 처음부터 입력 받기
            }

            // UserRegisterDto 객체 생성: 사용자 입력을 UserRegisterDto에 담기
            UserRegisterDto registerDto = new UserRegisterDto(emailId, password, nickname);

            try {
                // 회원가입 검증: UserService에서 제공하는 회원가입 메서드 호출
                boolean isRegistered = userService.register(registerDto); // 회원가입 성공 여부 반환
                if (isRegistered) {
                    // 회원가입 성공 시 메시지 출력
                    showRegistrationSuccess();

                    // 회원가입 성공 후 로그인 화면으로 이동
                    LoginViewImpl loginView = new LoginViewImpl(consoleHelper, userService); // 로그인 화면 뷰 생성
                    loginView.showLoginForm(); // 로그인 화면 표시

                    return; // 회원가입 성공 시 메서드 종료
                } else {
                    // 회원가입 실패 시 메시지 출력
                    showRegistrationFailed("회원가입에 실패했습니다. 다시 시도해주세요.");
                }
            } catch (Exception e) {
                // 예외 발생 시 디버깅 메시지 출력
                System.out.println("회원가입 중 오류 발생: " + e.getMessage());
                // 사용자에게 회원가입 실패 메시지 표시
                showRegistrationFailed("회원가입 처리 중 오류가 발생했습니다.");
            }
        }
    }

    @Override
    public void showRegistrationSuccess() {
        // 회원가입 성공 시 사용자 이름을 포함한 메시지 출력
        consoleHelper.displayHeader("→ 회원가입 완료! 로그인 화면으로 이동합니다.");
    }

    @Override
    public void showRegistrationFailed(String message) {
        // 회원가입 실패 시 메시지 출력
        consoleHelper.displayHeader("→ 회원가입 실패! " + message);
    }
}