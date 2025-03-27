package com.metaverse.mail.view.interfaces;

public interface MainMenuView {
    /**
     * 메인 메뉴 표시
     */
    void display();

    /**
     * 사용자 메뉴 선택 받기
     *
     * @return 선택한 메뉴 번호
     */
    int getMenuChoice();

    /**
     * 메뉴 실행
     *
     * @param choice 선택한 메뉴 번호
     */
    void executeMenu(int choice);
    
    /**
     * 사용자 정보 표시
     * 
     * @param username 사용자 이름
     */
    void showUserInfo(String username);
    
    /**
     * 시스템 알림 표시
     * 
     * @param message 알림 메시지
     */
    void showNotification(String message);
}
