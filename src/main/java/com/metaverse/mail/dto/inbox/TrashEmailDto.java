package com.metaverse.mail.dto.inbox;

import java.time.LocalDateTime;

public class TrashEmailDto {
    private int trashId;
    private int emailId;
    private String title;
    private String personName;  // 발신자/수신자
    private String emailType;   // "수신" 또는 "발신"
    private LocalDateTime deletedDate;
    private LocalDateTime expirationDate;

    // 생성자
    public TrashEmailDto() {}

    public TrashEmailDto(int trashId, int emailId, String title, String personName, String emailType, LocalDateTime deletedDate, LocalDateTime expirationDate) {
        this.trashId = trashId;
        this.emailId = emailId;
        this.title = title;
        this.personName = personName;
        this.emailType = emailType;
        this.deletedDate = deletedDate;
        this.expirationDate = expirationDate;
    }

    // getter, setter
    public int getTrashId() {
        return trashId;
    }

    public void setTrashId(int trashId) {
        this.trashId = trashId;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}