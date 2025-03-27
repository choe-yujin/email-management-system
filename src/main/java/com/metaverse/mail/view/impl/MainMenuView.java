package com.metaverse.mail.view.impl;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.view.interfaces.MenuView;

import java.util.Scanner;

/**
 * 애플리케이션의 메인 메뉴 화면을 구현하는 클래스
 * 
 * <p>이 클래스는 애플리케이션의 주요 진입점으로, 로그인 전/후의 메뉴를 표시하고
 * 사용자 선택에 따라 적절한 뷰로 전환합니다.</p>
 * 
 * <p>주요 기능:</p>
 * <ul>
 *   <li>로그인 전 메뉴 표시 (로그인, 회원가입, 종료)</li>
 *   <li>로그인 후 메뉴 표시 (메일 작성, 받은 메일함, 보낸 메일함 등)</li>
 *   <li>사용자 메뉴 선택 처리 및 해당 뷰로 전환</li>
 *   <li>로그아웃 및 종료 처리</li>
 * </ul>
 * 
 * @author 이메일 관리 시스템 팀
 * @version 1.0
 */
public class MainMenuView implements MenuView {
    /** 사용자 입력을 처리하는 Scanner 객체 */
    private Scanner scanner;
    
    /** 콘솔 UI 헬퍼 객체 */
    private ConsoleHelper consoleHelper;
    
    /** 사용자 세션 정보 */
    private Session session;

    /**
     * 메인 메뉴 뷰 생성자
     * 
     * <p>Scanner 객체를 받아 ConsoleHelper와 Session 객체를 초기화합니다.</p>
     * 
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     */
    public MainMenuView(Scanner scanner) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.session = Session.getInstance();
    }

    /**
     * 메인 메뉴 화면을 표시하고 사용자 입력을 처리하는 메인 메서드
     * 
     * <p>로그인 상태에 따라 적절한 메뉴(로그인 메뉴 또는 메인 메뉴)를 표시하고
     * 무한 루프를 통해 계속해서 사용자 입력을 처리합니다.</p>
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
     * 사용자의 메뉴 선택을 받는 메서드
     * 
     * <p>이 구현체에서는 직접 사용되지 않고, showLoginMenu()와 showMainMenu() 메서드에서
     * ConsoleHelper를 통해 사용자 입력을 직접 처리합니다.</p>
     * 
     * @return 선택한 메뉴 번호 (이 구현에서는 항상 0을 반환)
     */
    @Override
    public int getMenuChoice() {
        // 구현은 팀원들이 작성
        return 0;
    }

    /**
     * 선택된 메뉴를 실행하는 메서드
     * 
     * <p>이 구현체에서는 직접 사용되지 않고, showLoginMenu()와 showMainMenu() 메서드에서
     * switch 문을 통해 메뉴 실행을 직접 처리합니다.</p>
     * 
     * @param choice 실행할 메뉴 번호
     */
    @Override
    public void executeMenu(int choice) {
        // 구현은 팀원들이 작성
    }

    /**
     * 로그인 전 메뉴를 표시하고 사용자 선택을 처리하는 메서드
     * 
     * <p>로그인, 회원가입, 종료 옵션을 표시하고 사용자의 선택에 따라
     * 해당 기능을 수행하는 뷰로 전환합니다.</p>
     * 
     * <p>메뉴 옵션:</p>
     * <ol>
     *   <li>로그인 (팀원 A 개발 담당)</li>
     *   <li>회원가입 (팀원 A 개발 담당)</li>
     *   <li>종료 (프로그램 종료)</li>
     * </ol>
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

    /**
     * 로그인 후 메인 메뉴를 표시하고 사용자 선택을 처리하는 메서드
     * 
     * <p>메일 관리와 관련된 주요 기능들(메일 작성, 받은 메일함, 보낸 메일함 등)을 표시하고
     * 사용자의 선택에 따라 해당 기능을 수행하는 뷰로 전환합니다.</p>
     * 
     * <p>메뉴 옵션:</p>
     * <ol>
     *   <li>메일 작성 (팀원 B 개발 담당)</li>
     *   <li>받은 메일함 (팀원 B 개발 담당)</li>
     *   <li>보낸 메일함 (팀원 C 개발 담당)</li>
     *   <li>메일 검색 (팀원 B 개발 담당)</li>
     *   <li>휴지통 (팀원 C 개발 담당)</li>
     *   <li>회원 수정 (팀원 A 개발 담당)</li>
     *   <li>로그아웃 (세션 종료 및 로그인 메뉴로 이동)</li>
     * </ol>
     */
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