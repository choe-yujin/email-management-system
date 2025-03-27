package com.metaverse.mail.dto.mail;

import java.util.List;

public class EmailComposeDto {
    private List<String> receiverEmails;
    private String title;
    private String body;

    // 생성자
    public EmailComposeDto() {}

    public EmailComposeDto(List<String> receiverEmails, String title, String body) {
        this.receiverEmails = receiverEmails;
        this.title = title;
        this.body = body;
    }

    // Getter/Setter
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
}