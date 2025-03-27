package com.metaverse.mail.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Email {
    private int emailIdx;
    private int senderId;
    private String title;
    private String body;
    private char status;
    private LocalDateTime createdAt;

    // 생성자
    public Email() {}

    public Email(int emailIdx, int senderId, String title, String body, char status) {
        this.emailIdx = emailIdx;
        this.senderId = senderId;
        this.title = title;
        this.body = body;
        this.status = status;
    }

    // Getter/Setter
    public int getEmailIdx() {
        return emailIdx;
    }

    public void setEmailIdx(int emailIdx) {
        this.emailIdx = emailIdx;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // 날짜 포맷팅 유틸리티 메서드
    public String getFormattedDate() {
        if (createdAt == null) {
            return "";
        }
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailIdx=" + emailIdx +
                ", senderId=" + senderId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", createdAt=" + getFormattedDate() +
                '}';
    }
}