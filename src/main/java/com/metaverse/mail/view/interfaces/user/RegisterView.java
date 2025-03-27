package com.metaverse.mail.view.interfaces.user;

public interface RegisterView {
    /**
     * 회원가입 폼 표시
     */
    void showRegistrationForm();
    
    /**
     * 회원가입 성공 메시지 표시
     */
    void showRegistrationSuccess();
    
    /**
     * 회원가입 실패 메시지 표시
     * 
     * @param message 실패 이유
     */
    void showRegistrationFailed(String message);
}
