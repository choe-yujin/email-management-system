package com.metaverse.mail.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 휴지통에 있는 이메일 정보를 나타내는 모델 클래스
 * 
 * 이 클래스는 데이터베이스 TRASH 테이블의 레코드를 자바 객체로 표현합니다.
 * 삭제된 이메일의 유지 기간 및 복구 상태 등을 관리합니다.
 * 
 * 관련 테이블 구조:
 * CREATE TABLE TRASH (
 *     trash_idx       INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '휴지통 고유의 idx 값',
 *     link_id         INTEGER                                             NOT NULL COMMENT '링크드 아이디(FK)',
 *     deleted_at      DATETIME DEFAULT NOW()                              NOT NULL COMMENT '메일 삭제일',
 *     expiration_date DATETIME DEFAULT (DATE_ADD(NOW(), INTERVAL 30 DAY)) NOT NULL COMMENT '메일 영구삭제 예정일(30일 후)',
 *     is_restored     CHAR(1)  DEFAULT 'N'                                NOT NULL COMMENT '상태(Y:복구됨, N:삭제상태)',
 *     FOREIGN KEY (link_id) REFERENCES EMAIL_LINK (link_idx) ON DELETE CASCADE,
 *     CONSTRAINT chk_trash_restored CHECK (is_restored IN ('Y', 'N'))
 * );
 * 
 * @author 유진
 * @version 1.0
 */
public class Trash {
    /** 휴지통 항목 고유 식별자 */
    private int trashIdx;
    
    /** 이메일 링크 ID (EMAIL_LINK 테이블 참조) */
    private int linkId;
    
    /** 이메일 삭제 일시 */
    private LocalDateTime deletedAt;
    
    /** 이메일 영구 삭제 예정 일시 (기본: 삭제일로부터 30일 후) */
    private LocalDateTime expirationDate;
    
    /** 복구 상태 (Y: 복구됨, N: 삭제상태) */
    private char isRestored;

    /**
     * 기본 생성자
     */
    public Trash() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param trashIdx 휴지통 항목 ID
     * @param linkId 이메일 링크 ID
     * @param deletedAt 삭제 일시
     * @param expirationDate 만료 일시
     * @param isRestored 복구 상태 ('Y': 복구됨, 'N': 삭제상태)
     */
    public Trash(int trashIdx, int linkId, LocalDateTime deletedAt, LocalDateTime expirationDate, char isRestored) {
        this.trashIdx = trashIdx;
        this.linkId = linkId;
        this.deletedAt = deletedAt;
        this.expirationDate = expirationDate;
        this.isRestored = isRestored;
    }

    /**
     * 휴지통 항목 ID 반환
     * 
     * @return 휴지통 항목 ID
     */
    public int getTrashIdx() {
        return trashIdx;
    }

    /**
     * 휴지통 항목 ID 설정
     * 
     * @param trashIdx 설정할 휴지통 항목 ID
     */
    public void setTrashIdx(int trashIdx) {
        this.trashIdx = trashIdx;
    }

    /**
     * 이메일 링크 ID 반환
     * 
     * @return 이메일 링크 ID
     */
    public int getLinkId() {
        return linkId;
    }

    /**
     * 이메일 링크 ID 설정
     * 
     * @param linkId 설정할 이메일 링크 ID
     */
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    /**
     * 삭제 일시 반환
     * 
     * @return 삭제 일시
     */
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    /**
     * 삭제 일시 설정
     * 
     * @param deletedAt 설정할 삭제 일시
     */
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * 만료 일시 반환
     * 
     * @return 만료 일시
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * 만료 일시 설정
     * 
     * @param expirationDate 설정할 만료 일시
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * 복구 상태 반환
     * 
     * @return 복구 상태 문자 ('Y': 복구됨, 'N': 삭제상태)
     */
    public char getIsRestored() {
        return isRestored;
    }

    /**
     * 복구 상태 설정
     * 
     * @param isRestored 설정할 복구 상태 ('Y': 복구됨, 'N': 삭제상태)
     */
    public void setIsRestored(char isRestored) {
        this.isRestored = isRestored;
    }

    /**
     * 삭제 일시를 포맷팅하여 문자열로 반환
     * 
     * 포맷 형식: "yyyy-MM-dd HH:mm"
     * 
     * @return 포맷팅된 삭제 일시 문자열, 삭제 일시가 null인 경우 빈 문자열
     */
    public String getFormattedDeletedDate() {
        if (deletedAt == null) {
            return "";
        }
        return deletedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * 만료 일시를 포맷팅하여 문자열로 반환
     * 
     * 포맷 형식: "yyyy-MM-dd HH:mm"
     * 
     * @return 포맷팅된 만료 일시 문자열, 만료 일시가 null인 경우 빈 문자열
     */
    public String getFormattedExpirationDate() {
        if (expirationDate == null) {
            return "";
        }
        return expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Trash 객체의 문자열 표현을 반환합니다.
     * 
     * 휴지통 항목의 기본 정보를 포함합니다.
     * 
     * @return Trash 객체의 문자열 표현
     */
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