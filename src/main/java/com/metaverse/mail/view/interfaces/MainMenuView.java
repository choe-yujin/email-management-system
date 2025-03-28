package com.metaverse.mail.view.interfaces;

public interface MainMenuView {
    /**
     * 메인 메뉴 표시
     */
    void display();

    /**
     * 사용자 정보 표시
     * 
     * @param username 사용자 이름
     */
    void showUserInfo(String username);

}
