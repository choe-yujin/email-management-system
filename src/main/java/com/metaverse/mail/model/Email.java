package com.metaverse.mail.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 이메일 정보를 나타내는 모델 클래스
 * 
 * <p>이 클래스는 데이터베이스 EMAIL 테이블의 레코드를 자바 객체로 표현합니다.
 * 이메일의 기본 정보(발신자, 제목, 내용, 상태 등)를 포함합니다.</p>
 * 
 * <p>관련 테이블 구조:</p>
 * <pre>
 * CREATE TABLE EMAIL (
 *     email_idx  INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '이메일 고유의 idx 값',
 *     sender_id  INTEGER               NOT NULL COMMENT '발신자 ID',
 *     title      VARCHAR(100)          NOT NULL COMMENT '제목',
 *     body       LONGTEXT              NOT NULL COMMENT '내용',
 *     status     CHAR(1) DEFAULT 'N'   NOT NULL COMMENT '메일 발송 상태 확인(Y:발송완료, N:발송실패)',
 *     created_at DATETIME DEFAULT NOW() NOT NULL COMMENT '메일 발신일 표시',
 *     FOREIGN KEY (sender_id) REFERENCES USER (idx) ON DELETE CASCADE,
 *     CONSTRAINT chk_email_status CHECK (status IN ('Y', 'N'))
 * );
 * </pre>
 * 
 * @author 이메일 관리 시스템 팀
 * @version 1.0
 */
public class Email {
    /** 이메일 고유 식별자 */
    private int emailIdx;
    
    /** 발신자 사용자 ID */
    private int senderId;
    
    /** 이메일 제목 */
    private String title;
    
    /** 이메일 본문 내용 */
    private String body;
    
    /** 이메일 발송 상태 (Y: 발송 완료, N: 발송 실패) */
    private char status;
    
    /** 이메일 생성 일시 */
    private LocalDateTime createdAt;

    /**
     * 기본 생성자
     */
    public Email() {}

    /**
     * 주요 필드를 초기화하는 생성자
     * 
     * @param emailIdx 이메일 ID
     * @param senderId 발신자 ID
     * @param title 이메일 제목
     * @param body 이메일 내용
     * @param status 발송 상태 ('Y': 발송 완료, 'N': 발송 실패)
     */
    public Email(int emailIdx, int senderId, String title, String body, char status) {
        this.emailIdx = emailIdx;
        this.senderId = senderId;
        this.title = title;
        this.body = body;
        this.status = status;
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
     * 발신자 ID 반환
     * 
     * @return 발신자 ID
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * 발신자 ID 설정
     * 
     * @param senderId 설정할 발신자 ID
     */
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    /**
     * 이메일 제목 반환
     * 
     * @return 이메일 제목
     */
    public String getTitle() {
        return title;
    }

    /**
     * 이메일 제목 설정
     * 
     * @param title 설정할 이메일 제목
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 이메일 내용 반환
     * 
     * @return 이메일 내용
     */
    public String getBody() {
        return body;
    }

    /**
     * 이메일 내용 설정
     * 
     * @param body 설정할 이메일 내용
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 발송 상태 반환
     * 
     * @return 발송 상태 문자 ('Y': 발송 완료, 'N': 발송 실패)
     */
    public char getStatus() {
        return status;
    }

    /**
     * 발송 상태 설정
     * 
     * @param status 설정할 발송 상태 ('Y': 발송 완료, 'N': 발송 실패)
     */
    public void setStatus(char status) {
        this.status = status;
    }

    /**
     * 이메일 생성 일시 반환
     * 
     * @return 이메일 생성 일시
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 이메일 생성 일시 설정
     * 
     * @param createdAt 설정할 생성 일시
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 이메일 생성 일시를 포맷팅하여 문자열로 반환
     * 
     * <p>포맷 형식: "yyyy-MM-dd HH:mm"</p>
     * 
     * @return 포맷팅된 생성 일시 문자열, 생성 일시가 null인 경우 빈 문자열
     */
    public String getFormattedDate() {
        if (createdAt == null) {
            return "";
        }
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * 이메일 객체의 문자열 표현을 반환합니다.
     * 
     * <p>이메일의 기본 정보를 포함합니다.</p>
     * 
     * @return 이메일 객체의 문자열 표현
     */
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