package com.metaverse.mail.common;

import java.util.Scanner;

/**
 * 콘솔 UI 관련 공통 기능을 제공하는 유틸리티 클래스
 * 
 * <p>이 클래스는 콘솔 애플리케이션의 사용자 인터페이스와 관련된 다양한 헬퍼 메서드를 제공합니다:</p>
 * <ul>
 *   <li>제목 및 구분선 출력</li>
 *   <li>사용자 입력 처리 (정수, 문자열)</li>
 *   <li>입력 유효성 검사</li>
 *   <li>확인 메시지 표시 및 처리</li>
 * </ul>
 * 
 * <p>이 클래스는 모든 뷰 클래스에서 공통적으로 사용하여 UI 일관성을 유지하는 데 도움이 됩니다.</p>
 * 
 * @author 이메일 관리 시스템 팀
 * @version 1.0
 */
public class ConsoleHelper {
    /** 사용자 입력을 처리하는 Scanner 객체 */
    private Scanner scanner;

    /**
     * ConsoleHelper 생성자
     * 
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     */
    public ConsoleHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * 화면 상단에 제목을 표시합니다.
     * 
     * <p>제목을 구분선과 함께 강조하여 표시합니다.</p>
     * 
     * @param title 표시할 제목 텍스트
     */
    public void displayHeader(String title) {
        System.out.println("==========================================");
        System.out.println("      " + title);
        System.out.println("==========================================");
    }

    /**
     * 구분선을 표시합니다.
     * 
     * <p>섹션 구분을 위한 가로선을 출력합니다.</p>
     */
    public void displayDivider() {
        System.out.println("------------------------------------------");
    }

    /**
     * 사용자로부터 정수 입력을 받습니다.
     * 
     * <p>입력값이 지정된 범위 내에 있는지 검증하며, 유효하지 않은 경우 재입력을 요청합니다.</p>
     * 
     * @param prompt 사용자에게 표시할 입력 안내 메시지
     * @param min 유효한 최소값
     * @param max 유효한 최대값
     * @return 사용자가 입력한 유효한 정수값
     */
    public int getIntInput(String prompt, int min, int max) {
        int choice = -1;
        do {
            System.out.print("→ " + prompt);
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if (choice < min || choice > max) {
                    System.out.println("→ 유효한 범위가 아닙니다. 다시 입력해주세요.");
                    choice = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("→ 숫자만 입력해주세요.");
            }
        } while (choice < min || choice > max);

        return choice;
    }

    /**
     * 사용자로부터 문자열 입력을 받습니다.
     * 
     * <p>입력된 문자열 앞뒤의 공백을 제거하여 반환합니다.</p>
     * 
     * @param prompt 사용자에게 표시할 입력 안내 메시지
     * @return 사용자가 입력한 문자열(공백 제거)
     */
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * 비밀번호 입력을 받습니다.
     * 
     * <p>콘솔에서는 마스킹 처리가 어려워 일반 문자열 입력과 동일하게 처리합니다.
     * 실제 구현에서는 더 안전한 방식으로 변경할 수 있습니다.</p>
     * 
     * @param prompt 사용자에게 표시할 입력 안내 메시지
     * @return 사용자가 입력한 비밀번호
     */
    public String getPasswordInput(String prompt) {
        return getStringInput(prompt); // 콘솔에서는 마스킹 어려워 일반 입력과 동일하게 처리
    }

    /**
     * 사용자에게 확인(예/아니오) 메시지를 표시하고 응답을 받습니다.
     * 
     * <p>사용자는 'y', 'yes', 'n', 'no' 중 하나로 답변할 수 있으며,
     * 'y' 또는 'yes'의 경우 true를, 그 외의 경우 false를 반환합니다.</p>
     * 
     * @param prompt 사용자에게 표시할 확인 메시지
     * @return 사용자 확인 여부 (예: true, 아니오: false)
     */
    public boolean getConfirmation(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}