package com.metaverse.mail.view.interfaces.user;

public interface LoginView {
    /**
     * 로그인 폼 표시
     */
    void showLoginForm();
    
    /**
     * 로그인 성공 메시지 표시
     * 
     * @param username 로그인한 사용자 이름
     */
    void showLoginSuccess(String username);
    
    /**
     * 로그인 실패 메시지 표시
     */
    void showLoginFailed();
}
