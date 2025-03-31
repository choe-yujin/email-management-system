package com.metaverse.mail.view.impl;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dao.impl.inbox.EmailLinkDaoImpl;
import com.metaverse.mail.dao.impl.mail.EmailDaoImpl;
import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dao.mock.MockEmailLinkDao;
import com.metaverse.mail.dao.mock.MockUserDao;
import com.metaverse.mail.service.impl.mail.EmailServiceImpl;
import com.metaverse.mail.service.impl.user.UserServiceImpl;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.service.interfaces.UserService;
import com.metaverse.mail.view.impl.mail.ComposeViewImpl;
import com.metaverse.mail.view.impl.user.LoginViewImpl;
import com.metaverse.mail.view.impl.mail.InboxViewImpl;
import com.metaverse.mail.view.interfaces.MainMenuView;
import com.metaverse.mail.view.interfaces.mail.ComposeView;
import com.metaverse.mail.view.interfaces.user.LoginView;
import com.metaverse.mail.view.interfaces.mail.InboxView;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 애플리케이션의 메인 메뉴 화면을 구현하는 클래스
 *
 * 이 클래스는 애플리케이션의 주요 진입점으로, 로그인 전/후의 메뉴를 표시하고
 * 사용자 선택에 따라 적절한 뷰로 전환합니다.
 *
 * 주요 기능:
 *
 *   로그인 전 메뉴 표시 (로그인, 회원가입, 종료)
 *   로그인 후 메뉴 표시 (메일 작성, 받은 메일함, 보낸 메일함 등)
 *   사용자 메뉴 선택 처리 및 해당 뷰로 전환
 *   로그아웃 및 종료 처리
 *
 * @author 유진
 * @version 1.0
 */
public class MainMenuViewImpl implements MainMenuView {
    /** 사용자 입력을 처리하는 Scanner 객체 */
    private Scanner scanner;

    /** 콘솔 UI 헬퍼 객체 */
    private ConsoleHelper consoleHelper;

    /** 사용자 세션 정보 */
    private Session session;

    /** JDBC connection 객체 */
    private Connection connection;

    /**
     * 메인 메뉴 뷰 생성자
     *
     * Scanner 객체를 받아 ConsoleHelper와 Session 객체를 초기화합니다.
     *
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     */

    // 생성자에서 Connection 초기화
    public MainMenuViewImpl(Scanner scanner) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.session = Session.getInstance();

        try {
            this.connection = JDBCConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패!", e);
        }
    }

    /**
     * 메인 메뉴 화면을 표시하고 사용자 입력을 처리하는 메인 메서드
     *
     * 로그인 상태에 따라 적절한 메뉴(로그인 메뉴 또는 메인 메뉴)를 표시하고
     * 무한 루프를 통해 계속해서 사용자 입력을 처리합니다.
     */
    @Override
    public void display() {
        while (true) {
            if (!session.isLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    /**
     * 사용자 정보 표시
     *
     * @param username 사용자 이름
     */
    @Override
    public void showUserInfo(String username) {
        consoleHelper.displayHeader("사용자 정보");
        System.out.println("현재 로그인된 사용자: " + username);
        consoleHelper.displayDivider();
    }

    /**
     * 로그인 전 메뉴를 표시하고 사용자 선택을 처리하는 메서드
     *
     * 로그인, 회원가입, 종료 옵션을 표시하고 사용자의 선택에 따라
     * 해당 기능을 수행하는 뷰로 전환합니다.
     *
     * 메뉴 옵션:
     *   로그인 (팀원 A 개발 담당)
     *   회원가입 (팀원 A 개발 담당)
     *   종료 (프로그램 종료)
     */
    private void showLoginMenu() {
        consoleHelper.displayHeader("📩 메일 관리 시스템 (Console)");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");
        consoleHelper.displayDivider();

        int choice = consoleHelper.getIntInput("원하는 기능을 선택하세요 (1-3): ", 1, 3);

        switch (choice) {
            case 1:
                // 로그인 뷰 (팀원 A가 구현)
                LoginView loginView = createLoginView(); // LoginView 객체 생성
                loginView.showLoginForm(); // 로그인 폼 화면 표시
                break;
            case 2:
                // 회원가입 뷰 (팀원 A가 구현)
                System.out.println("[스켈톤] 회원가입 뷰는 팀원 A가 구현할 예정입니다.");
                break;
            case 3:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
                break;
        }
    }

    private LoginView createLoginView() {
        UserDao userDao = new UserDaoImpl(connection); // UserDaoImpl에 전달
        UserService userService = new UserServiceImpl(userDao);
        return new LoginViewImpl(consoleHelper, userService);
    }

    /**
     * 로그인 후 메인 메뉴를 표시하고 사용자 선택을 처리하는 메서드
     *
     * 메일 관리와 관련된 주요 기능들(메일 작성, 받은 메일함, 보낸 메일함 등)을 표시하고
     * 사용자의 선택에 따라 해당 기능을 수행하는 뷰로 전환합니다.
     *
     * 메뉴 옵션:
     *   메일 작성 (팀원 B 개발 담당)
     *   받은 메일함 (팀원 B 개발 담당)
     *   보낸 메일함 (팀원 C 개발 담당)
     *   메일 검색 (팀원 B 개발 담당)
     *   휴지통 (팀원 C 개발 담당)
     *   회원 수정 (팀원 A 개발 담당)
     *   로그아웃 (세션 종료 및 로그인 메뉴로 이동)
     */
    public void showMainMenu() {
        consoleHelper.displayHeader("📩 메일 관리 시스템 (Main)");
        System.out.println("1. 메일 작성");
        System.out.println("2. 받은 메일함");
        System.out.println("3. 보낸 메일함");
        System.out.println("4. 메일 검색");
        System.out.println("5. 휴지통");
        System.out.println("6. 회원 수정");
        System.out.println("7. 로그아웃");
        consoleHelper.displayDivider();

        int choice = consoleHelper.getIntInput("원하는 기능을 선택하세요 (1-7): ", 1, 7);

        switch (choice) {
            case 1:
                // 메일 작성 - 유진 구현
                ComposeView composeView = createComposeView();
                composeView.showComposeForm();
                break;
            case 2:
                // 받은 메일함 - 유진 구현
                InboxView inboxView = createInboxView();
                inboxView.showInbox();
                break;
            case 3:
                // 보낸 메일함 (팀원 C가 구현)
                System.out.println("[스켈톤] 보낸 메일함 뷰는 팀원 C가 구현할 예정입니다.");
                break;
            case 4:
                // 메일 검색 (팀원 B가 구현)
                System.out.println("[스켈톤] 메일 검색 뷰는 팀원 B가 구현할 예정입니다.");
                break;
            case 5:
                // 휴지통 (팀원 C가 구현)
                System.out.println("[스켈톤] 휴지통 뷰는 팀원 C가 구현할 예정입니다.");
                break;
            case 6:
                // 회원 수정 (팀원 A가 구현)
                System.out.println("[스켈톤] 회원 수정 뷰는 팀원 A가 구현할 예정입니다.");
                break;
            case 7:
                session.logout();
                System.out.println("→ 로그아웃 되었습니다.");
                break;
        }
    }

    /**
     * 메일 작성 화면 객체 생성
     *
     * 메일 작성에 필요한 DAO, Service 객체들을 생성하고
     * 이를 주입받은 ComposeView 구현체를 반환합니다.
     *
     * @return 메일 작성 화면 객체
     */
    private ComposeView createComposeView() {
        EmailDao emailDao = new EmailDaoImpl(connection);
        EmailLinkDao emailLinkDao = new EmailLinkDaoImpl(connection);
        UserDao userDao = new UserDaoImpl(connection);

        // Service 객체 생성
        EmailService emailService = new EmailServiceImpl(emailDao, emailLinkDao, userDao);

        // View 객체 생성 및 반환
        return new ComposeViewImpl(scanner, emailService);
    }

    private InboxView createInboxView() {
        EmailDao emailDao = new EmailDaoImpl(connection);
        EmailLinkDao emailLinkDao = new EmailLinkDaoImpl(connection);
        UserDao userDao = new UserDaoImpl(connection);

        // Service 객체 생성
        EmailService emailService = new EmailServiceImpl(emailDao, emailLinkDao, userDao);

        // View 객체 생성 및 반환
        return new InboxViewImpl(scanner, emailService);
    }
}