package com.metaverse.mail.dto.user;

public class UserLoginDto {
    private String emailId;
    private String password;

    // 생성자
    public UserLoginDto() {}

    public UserLoginDto(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    // Getter/Setter
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}