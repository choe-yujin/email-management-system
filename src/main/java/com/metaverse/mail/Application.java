package com.metaverse.mail;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.view.impl.MainMenuView;

import java.util.Scanner;

public class Application {
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