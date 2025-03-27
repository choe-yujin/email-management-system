package com.metaverse.mail.view.interfaces;

public interface MenuView {
    /**
     * 메뉴 화면 표시
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
}