package com.metaverse.mail.dto.user;

public class UserProfileDto {
    private String nickname;
    private String currentPassword;
    private String newPassword;

    // 생성자
    public UserProfileDto() {}

    public UserProfileDto(String nickname, String currentPassword, String newPassword) {
        this.nickname = nickname;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    // Getter/Setter
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
