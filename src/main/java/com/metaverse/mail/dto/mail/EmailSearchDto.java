package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;

public class EmailSearchDto {
    private int emailId;
    private String title;
    private String personName; // 발신자 또는 수신자
    private String emailType;  // "수신" 또는 "발신"
    private boolean isRead;
    private LocalDateTime sentDate;

    // 생성자
    public EmailSearchDto() {}

    public EmailSearchDto(int emailId, String title, String personName, String emailType, boolean isRead, LocalDateTime sentDate) {
        this.emailId = emailId;
        this.title = title;
        this.personName = personName;
        this.emailType = emailType;
        this.isRead = isRead;
        this.sentDate = sentDate;
    }

    // getter, setter
    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }
}