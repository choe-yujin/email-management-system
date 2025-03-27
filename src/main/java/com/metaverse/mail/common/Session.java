package com.metaverse.mail.common;

import com.metaverse.mail.model.User;

public class Session {
    private static Session instance;
    private User currentUser;
    private boolean loggedIn = false;

    private Session() {} // 싱글톤 패턴

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public boolean login(User user) {
        if (user != null) {
            this.currentUser = user;
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
        this.loggedIn = false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getIdx() : -1;
    }

    public String getCurrentUserEmail() {
        return currentUser != null ? currentUser.getEmailId() : null;
    }

    public String getCurrentUserNickname() {
        return currentUser != null ? currentUser.getNickname() : null;
    }
}