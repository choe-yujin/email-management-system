package com.metaverse.mail.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 사용자 정보를 나타내는 모델 클래스
 * 
 * 이 클래스는 데이터베이스 USER 테이블의 레코드를 자바 객체로 표현합니다.
 * 사용자의 기본 정보 및 계정 상태를 포함합니다.
 * 
 * 관련 테이블 구조:
 * CREATE TABLE USER (
 *     idx        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '회원 고유의 idx값',
 *     email_id   VARCHAR(100)           NOT NULL UNIQUE COMMENT '회원 이메일 아이디',
 *     email_pwd  VARCHAR(255)           NOT NULL COMMENT '회원 비밀번호',
 *     nickname   VARCHAR(30)            NOT NULL COMMENT '회원 닉네임',
 *     status     CHAR(1)  DEFAULT 'A'   NOT NULL COMMENT '회원 상태(A:활성화, D:탈퇴)',
 *     created_at DATETIME DEFAULT NOW() NOT NULL COMMENT '회원 등록일',
 *     updated_at DATETIME COMMENT '회원 수정일',
 *     deleted_at DATETIME COMMENT '회원 삭제일',
 *     CONSTRAINT chk_user_status CHECK (status IN ('A', 'D'))
 * );
 * 
 * @author 유진
 * @version 1.0
 */
public class User {
    /** 사용자 고유 식별자 */
    private int idx;
    
    /** 사용자 이메일 주소 (로그인 ID로 사용) */
    private String emailId;
    
    /** 사용자 비밀번호 (암호화 권장) */
    private String emailPwd;
    
    /** 사용자 닉네임 */
    private String nickname;
    
    /** 계정 상태 (A: 활성화, D: 탈퇴) */
    private char status;
    
    /** 계정 생성 일시 */
    private LocalDateTime createdAt;
    
    /** 계정 정보 수정 일시 */
    private LocalDateTime updatedAt;
    
    /** 계정 삭제 일시 */
    private LocalDateTime deletedAt;

    /**
     * 기본 생성자
     */
    public User() {}

    /**
     * 주요 필드를 초기화하는 생성자
     * 
     * @param idx 사용자 ID
     * @param emailId 사용자 이메일 주소
     * @param emailPwd 사용자 비밀번호
     * @param nickname 사용자 닉네임
     * @param status 계정 상태 ('A': 활성화, 'D': 탈퇴)
     */
    public User(int idx, String emailId, String emailPwd, String nickname, char status) {
        this.idx = idx;
        this.emailId = emailId;
        this.emailPwd = emailPwd;
        this.nickname = nickname;
        this.status = status;
    }

    /**
     * 사용자 ID 반환
     * 
     * @return 사용자 ID
     */
    public int getIdx() {
        return idx;
    }

    /**
     * 사용자 ID 설정
     * 
     * @param idx 설정할 사용자 ID
     */
    public void setIdx(int idx) {
        this.idx = idx;
    }

    /**
     * 이메일 주소 반환
     * 
     * @return 사용자 이메일 주소
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * 이메일 주소 설정
     * 
     * @param emailId 설정할 이메일 주소
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * 비밀번호 반환
     * 
     * @return 사용자 비밀번호
     */
    public String getEmailPwd() {
        return emailPwd;
    }

    /**
     * 비밀번호 설정
     * 
     * @param emailPwd 설정할 비밀번호
     */
    public void setEmailPwd(String emailPwd) {
        this.emailPwd = emailPwd;
    }

    /**
     * 닉네임 반환
     * 
     * @return 사용자 닉네임
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 닉네임 설정
     * 
     * @param nickname 설정할 닉네임
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 계정 상태 반환
     * 
     * @return 계정 상태 문자 ('A': 활성화, 'D': 탈퇴)
     */
    public char getStatus() {
        return status;
    }

    /**
     * 계정 상태 설정
     * 
     * @param status 설정할 계정 상태 ('A': 활성화, 'D': 탈퇴)
     */
    public void setStatus(char status) {
        this.status = status;
    }

    /**
     * 계정 생성 일시 반환
     * 
     * @return 계정 생성 일시
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 계정 생성 일시 설정
     * 
     * @param createdAt 설정할 생성 일시
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 계정 수정 일시 반환
     * 
     * @return 계정 수정 일시
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 계정 수정 일시 설정
     * 
     * @param updatedAt 설정할 수정 일시
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 계정 삭제 일시 반환
     * 
     * @return 계정 삭제 일시
     */
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    /**
     * 계정 삭제 일시 설정
     * 
     * @param deletedAt 설정할 삭제 일시
     */
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * 사용자 객체의 문자열 표현을 반환합니다.
     * 
     * 사용자의 기본 정보를 포함합니다.
     * 
     * @return 사용자 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "User{" +
                "idx=" + idx +
                ", emailId='" + emailId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status=" + status +
                '}';
    }
}