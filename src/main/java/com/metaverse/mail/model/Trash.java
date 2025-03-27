package com.metaverse.mail.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trash {
    private int trashIdx;
    private int linkId;
    private LocalDateTime deletedAt;
    private LocalDateTime expirationDate;
    private char isRestored;

    // 생성자
    public Trash() {}

    public Trash(int trashIdx, int linkId, LocalDateTime deletedAt, LocalDateTime expirationDate, char isRestored) {
        this.trashIdx = trashIdx;
        this.linkId = linkId;
        this.deletedAt = deletedAt;
        this.expirationDate = expirationDate;
        this.isRestored = isRestored;
    }

    // Getter/Setter
    public int getTrashIdx() {
        return trashIdx;
    }

    public void setTrashIdx(int trashIdx) {
        this.trashIdx = trashIdx;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public char getIsRestored() {
        return isRestored;
    }

    public void setIsRestored(char isRestored) {
        this.isRestored = isRestored;
    }

    // 날짜 포맷팅 유틸리티 메서드
    public String getFormattedDeletedDate() {
        if (deletedAt == null) {
            return "";
        }
        return deletedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getFormattedExpirationDate() {
        if (expirationDate == null) {
            return "";
        }
        return expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public String toString() {
        return "Trash{" +
                "trashIdx=" + trashIdx +
                ", linkId=" + linkId +
                ", deletedAt=" + getFormattedDeletedDate() +
                ", expirationDate=" + getFormattedExpirationDate() +
                ", isRestored=" + isRestored +
                '}';
    }
}