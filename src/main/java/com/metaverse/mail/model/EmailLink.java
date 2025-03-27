package com.metaverse.mail.model;

/**
 * 이메일과 수신자 간의 연결 정보를 나타내는 모델 클래스
 * 
 * 이 클래스는 데이터베이스 EMAIL_LINK 테이블의 레코드를 자바 객체로 표현합니다.
 * 각 수신자에 대한 이메일 읽음 여부와 삭제 여부 등의 상태를 추적합니다.
 * 
 * 관련 테이블 구조:
 * CREATE TABLE EMAIL_LINK (
 *     link_idx    INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '이메일 링크드 고유의 idx 값',
 *     receiver_id INTEGER             NOT NULL COMMENT '수신자ID',
 *     email_idx   INTEGER             NOT NULL COMMENT '이메일 고유의 idx 값',
 *     is_readed   CHAR(1) DEFAULT 'N' NOT NULL COMMENT '메일 읽음 여부(Y:읽음, N:안읽음)',
 *     is_deleted  CHAR(1) DEFAULT 'N' NOT NULL COMMENT '메일 삭제 여부(Y:삭제, N:정상)',
 *     FOREIGN KEY (receiver_id) REFERENCES USER (idx) ON DELETE CASCADE,
 *     FOREIGN KEY (email_idx) REFERENCES EMAIL (email_idx) ON DELETE CASCADE,
 *     CONSTRAINT chk_email_link_read CHECK (is_readed IN ('Y', 'N')),
 *     CONSTRAINT chk_email_link_deleted CHECK (is_deleted IN ('Y', 'N'))
 * );
 * 
 * @author 유진
 * @version 1.0
 */
public class EmailLink {
    /** 이메일 링크 고유 식별자 */
    private int linkIdx;
    
    /** 수신자 사용자 ID */
    private int receiverId;
    
    /** 이메일 ID (EMAIL 테이블 참조) */
    private int emailIdx;
    
    /** 읽음 여부 (Y: 읽음, N: 안읽음) */
    private char isReaded;
    
    /** 삭제 여부 (Y: 삭제됨, N: 정상) */
    private char isDeleted;

    /**
     * 기본 생성자
     */
    public EmailLink() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param linkIdx 이메일 링크 ID
     * @param receiverId 수신자 ID
     * @param emailIdx 이메일 ID
     * @param isReaded 읽음 여부 ('Y': 읽음, 'N': 안읽음)
     * @param isDeleted 삭제 여부 ('Y': 삭제됨, 'N': 정상)
     */
    public EmailLink(int linkIdx, int receiverId, int emailIdx, char isReaded, char isDeleted) {
        this.linkIdx = linkIdx;
        this.receiverId = receiverId;
        this.emailIdx = emailIdx;
        this.isReaded = isReaded;
        this.isDeleted = isDeleted;
    }

    /**
     * 이메일 링크 ID 반환
     * 
     * @return 이메일 링크 ID
     */
    public int getLinkIdx() {
        return linkIdx;
    }

    /**
     * 이메일 링크 ID 설정
     * 
     * @param linkIdx 설정할 이메일 링크 ID
     */
    public void setLinkIdx(int linkIdx) {
        this.linkIdx = linkIdx;
    }

    /**
     * 수신자 ID 반환
     * 
     * @return 수신자 ID
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * 수신자 ID 설정
     * 
     * @param receiverId 설정할 수신자 ID
     */
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 이메일 ID 반환
     * 
     * @return 이메일 ID
     */
    public int getEmailIdx() {
        return emailIdx;
    }

    /**
     * 이메일 ID 설정
     * 
     * @param emailIdx 설정할 이메일 ID
     */
    public void setEmailIdx(int emailIdx) {
        this.emailIdx = emailIdx;
    }

    /**
     * 읽음 여부 반환
     * 
     * @return 읽음 여부 문자 ('Y': 읽음, 'N': 안읽음)
     */
    public char getIsReaded() {
        return isReaded;
    }

    /**
     * 읽음 여부 설정
     * 
     * @param isReaded 설정할 읽음 여부 ('Y': 읽음, 'N': 안읽음)
     */
    public void setIsReaded(char isReaded) {
        this.isReaded = isReaded;
    }

    /**
     * 삭제 여부 반환
     * 
     * @return 삭제 여부 문자 ('Y': 삭제됨, 'N': 정상)
     */
    public char getIsDeleted() {
        return isDeleted;
    }

    /**
     * 삭제 여부 설정
     * 
     * @param isDeleted 설정할 삭제 여부 ('Y': 삭제됨, 'N': 정상)
     */
    public void setIsDeleted(char isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * EmailLink 객체의 문자열 표현을 반환합니다.
     * 
     * 이메일 링크의 기본 정보를 포함합니다.
     * 
     * @return EmailLink 객체의 문자열 표현
     */
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