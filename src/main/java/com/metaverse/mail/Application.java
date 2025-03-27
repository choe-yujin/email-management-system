package com.metaverse.mail;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.view.impl.MainMenuView;

import java.util.Scanner;

/**
 * 이메일 관리 시스템의 진입점 클래스
 * 
 * <p>이 클래스는 프로그램의 시작점으로, 다음과 같은 기능을 담당합니다:</p>
 * <ul>
 *   <li>콘솔 입력을 위한 Scanner 객체 생성</li>
 *   <li>메인 메뉴 화면 표시 및 사용자 상호작용 처리</li>
 *   <li>프로그램 종료 시 자원 정리</li>
 * </ul>
 * 
 * @author 이메일 관리 시스템 팀
 * @version 1.0
 */
public class Application {
    /**
     * 프로그램의 시작점
     * 
     * <p>이 메서드는 다음 단계로 실행됩니다:</p>
     * <ol>
     *   <li>콘솔 입력을 위한 Scanner 객체 생성</li>
     *   <li>사용자 인터페이스 제공을 위한 ConsoleHelper 생성</li>
     *   <li>메인 메뉴 화면(MainMenuView) 생성 및 표시</li>
     *   <li>프로그램 종료 시 Scanner 및 DB 연결 자원 정리</li>
     * </ol>
     * 
     * <p>예외 발생 시 오류 메시지를 콘솔에 출력하고 스택 트레이스를 표시합니다.</p>
     * 
     * @param args 명령행 인수 (사용하지 않음)
     */
    public static void main(String[] args) {
        try {
            // 스캐너 생성
            Scanner scanner = new Scanner(System.in);
            ConsoleHelper consoleHelper = new ConsoleHelper(scanner);

            // 메인 메뉴 화면 생성 및 표시
            MainMenuView mainMenuView = new MainMenuView(scanner);
            mainMenuView.display();

            // 종료 시 자원 정리
            scanner.close();
            JDBCConnection.close();
        } catch (Exception e) {
            System.err.println("프로그램 실행 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }
}