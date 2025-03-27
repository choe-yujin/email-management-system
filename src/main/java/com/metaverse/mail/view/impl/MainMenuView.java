package com.metaverse.mail.view.impl;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.view.interfaces.MenuView;

import java.util.Scanner;

public class MainMenuView implements MenuView {
    private Scanner scanner;
    private ConsoleHelper consoleHelper;
    private Session session;

    public MainMenuView(Scanner scanner) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.session = Session.getInstance();
    }

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

    @Override
    public int getMenuChoice() {
        // 구현은 팀원들이 작성
        return 0;
    }

    @Override
    public void executeMenu(int choice) {
        // 구현은 팀원들이 작성
    }

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
                System.out.println("[스켈톤] 로그인 뷰는 팀원 A가 구현할 예정입니다.");
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

    private void showMainMenu() {
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
                // 메일 작성 (팀원 B가 구현)
                System.out.println("[스켈톤] 메일 작성 뷰는 팀원 B가 구현할 예정입니다.");
                break;
            case 2:
                // 받은 메일함 (팀원 B가 구현)
                System.out.println("[스켈톤] 받은 메일함 뷰는 팀원 B가 구현할 예정입니다.");
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
}