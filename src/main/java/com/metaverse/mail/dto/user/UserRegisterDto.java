package com.metaverse.mail.dto.user;

public class UserRegisterDto {
    private String emailId;
    private String password;
    private String nickname;

    // 생성자
    public UserRegisterDto() {}

    public UserRegisterDto(String emailId, String password, String nickname) {
        this.emailId = emailId;
        this.password = password;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}