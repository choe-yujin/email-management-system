package com.metaverse.mail.view.impl.user;

import com.metaverse.mail.common.Constants;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.user.UserLoginDto;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.UserService;
import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.view.impl.MainMenuViewImpl;
import com.metaverse.mail.view.interfaces.user.LoginView;

import java.util.Scanner;

public class LoginViewImpl implements LoginView {
    private final UserService userService; // UserService 객체: 로그인 검증을 담당
    private final ConsoleHelper consoleHelper; // ConsoleHelper 객체: 콘솔 입출력을 담당
    private final Scanner scanner; // Scanner 객체 추가

    // LoginView 생성자: ConsoleHelper와 UserService 객체를 주입받아 초기화
    public LoginViewImpl(ConsoleHelper consoleHelper, UserService userService) {
        this.consoleHelper = consoleHelper;
        this.userService = userService;
        this.scanner = new Scanner(System.in); // Scanner 초기화 추가
    }
    @Override
    public void showLoginForm() {
        while (true) {
            // 로그인 화면 제목 출력
            consoleHelper.displayHeader("🔐 로그인");

            // 사용자로부터 아이디와 비밀번호를 입력 받음 (아이디는 이메일 형식으로 변환됨)
            String userId = consoleHelper.getStringInput("아이디: ");
            String emailId = Constants.toEmail(userId);
            String password = consoleHelper.getPasswordInput("비밀번호: ");
            consoleHelper.displayDivider(); // 구분선 출력

            // 아이디나 비밀번호가 비어있으면 다시 입력 받도록 함
            if (emailId.isEmpty() || password.isEmpty()) {
                System.out.println("→ 아이디와 비밀번호를 모두 입력하세요.");
                continue; // 다시 처음부터 입력 받기
            }

            // UserLoginDto 객체 생성: 사용자 입력을 UserLoginDto에 담기
            UserLoginDto loginDto = new UserLoginDto(emailId, password);

            try {
                // 로그인 검증: UserService에서 제공하는 로그인 메서드 호출
                User user = userService.login(loginDto);  // 로그인 검증 후 User 객체 반환
                if (user != null) {
                    // 로그인 성공 시 세션에 사용자 정보 저장
                    Session.getInstance().login(user);

                    // 로그인 성공 시 사용자의 닉네임을 포함한 성공 메시지 출력
                    showLoginSuccess(user.getNickname());

                    // 로그인 성공 후 메인 메뉴로 이동
                    MainMenuViewImpl mainMenuView = new MainMenuViewImpl(scanner);
                    mainMenuView.display();

                    return; // 로그인 성공 시 메서드 종료
                } else {
                    // 로그인 실패 시 메시지 출력
                    showLoginFailed();
                }
            } catch (Exception e) {
                // 예외 발생 시 디버깅 메시지 출력
                System.out.println("로그인 중 오류 발생: " + e.getMessage());
                // 사용자에게 로그인 실패 메시지 표시
                showLoginFailed(); // 로그인 실패 처리
            }

            // 로그인 실패 시, 다시 입력하거나 종료할 수 있는 선택지 제공
            int choice;
            do {
                System.out.println("1. 다시 입력하기");
                System.out.println("2. 종료");
                choice = consoleHelper.getIntInput("선택 (1-2): ", 1, 2);
            } while (choice != 1 && choice != 2); // 잘못된 선택지 입력 시 반복

            // 종료 선택 시 로그인 화면 종료
            if (choice == 2) {
                consoleHelper.displayHeader("→ 로그인 화면을 종료합니다.");
                break; // 로그인 화면 종료
            }
        }
    }

    @Override
    public void showLoginSuccess(String username) {
        // 로그인 성공 시 사용자 이름을 포함한 메시지 출력
        consoleHelper.displayHeader("→ " + username + "님, 로그인 성공! 메인 메뉴로 이동합니다.");
    }

    @Override
    public void showLoginFailed() {
        // 로그인 실패 시 메시지 출력
        consoleHelper.displayHeader("→ 해당 계정은 탈퇴 처리된 계정이거나, 아이디 또는 비밀번호가 틀렸습니다.");
    }
}
