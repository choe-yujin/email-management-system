package com.metaverse.mail.model;

public class EmailLink {
    private int linkIdx;
    private int receiverId;
    private int emailIdx;
    private char isReaded;
    private char isDeleted;

    // 생성자
    public EmailLink() {}

    public EmailLink(int linkIdx, int receiverId, int emailIdx, char isReaded, char isDeleted) {
        this.linkIdx = linkIdx;
        this.receiverId = receiverId;
        this.emailIdx = emailIdx;
        this.isReaded = isReaded;
        this.isDeleted = isDeleted;
    }

    // Getter/Setter
    public int getLinkIdx() {
        return linkIdx;
    }

    public void setLinkIdx(int linkIdx) {
        this.linkIdx = linkIdx;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getEmailIdx() {
        return emailIdx;
    }

    public void setEmailIdx(int emailIdx) {
        this.emailIdx = emailIdx;
    }

    public char getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(char isReaded) {
        this.isReaded = isReaded;
    }

    public char getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(char isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "EmailLink{" +
                "linkIdx=" + linkIdx +
                ", receiverId=" + receiverId +
                ", emailIdx=" + emailIdx +
                ", isReaded=" + isReaded +
                ", isDeleted=" + isDeleted +
                '}';
    }
}