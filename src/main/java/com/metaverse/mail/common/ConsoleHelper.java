package com.metaverse.mail.common;

import java.util.Scanner;

public class ConsoleHelper {
    private Scanner scanner;

    public ConsoleHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayHeader(String title) {
        System.out.println("==========================================");
        System.out.println("      " + title);
        System.out.println("==========================================");
    }

    public void displayDivider() {
        System.out.println("------------------------------------------");
    }

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

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String getPasswordInput(String prompt) {
        return getStringInput(prompt); // 콘솔에서는 마스킹 어려워 일반 입력과 동일하게 처리
    }

    public boolean getConfirmation(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}