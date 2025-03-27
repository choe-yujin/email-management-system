package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceivedEmailDto {
    private int emailId;
    private String senderName;
    private String senderEmail;
    private String title;
    private String body;
    private boolean isRead;
    private LocalDateTime sentDate;

    // 생성자
    public ReceivedEmailDto() {}

    // Getter/Setter
    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    // 유틸리티 메서드
    public String getFormattedDate() {
        if (sentDate == null) {
            return "";
        }
        return sentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}