package com.metaverse.mail.dto.inbox;

import java.time.LocalDateTime;
import java.util.List;

public class SentEmailDto {
    private int emailId;
    private List<String> receiverNames;
    private List<String> receiverEmails;
    private String title;
    private String body;
    private LocalDateTime sentDate;

    // 생성자
    public SentEmailDto() {}

    public SentEmailDto(int emailId, List<String> receiverNames, List<String> receiverEmails, String title, String body, LocalDateTime sentDate) {
        this.emailId = emailId;
        this.receiverNames = receiverNames;
        this.receiverEmails = receiverEmails;
        this.title = title;
        this.body = body;
        this.sentDate = sentDate;
    }

    // getter, setter
    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public List<String> getReceiverNames() {
        return receiverNames;
    }

    public void setReceiverNames(List<String> receiverNames) {
        this.receiverNames = receiverNames;
    }

    public List<String> getReceiverEmails() {
        return receiverEmails;
    }

    public void setReceiverEmails(List<String> receiverEmails) {
        this.receiverEmails = receiverEmails;
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

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }
}
