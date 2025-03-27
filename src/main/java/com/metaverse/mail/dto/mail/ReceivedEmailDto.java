package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 받은 이메일 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 받은 메일함에서 표시할 이메일 정보(발신자, 제목, 내용, 읽음 여부 등)를
 * 서비스 계층에서 프레젠테이션 계층으로 전달하는 데 사용됩니다.
 * 
 * DTO 패턴을 사용하여 비즈니스 계층과 프레젠테이션 계층 사이의
 * 데이터 교환을 명확하게 정의합니다.
 * 
 * 담당 개발자: 유진(개발자 B)
 * 
 * @author 유진
 * @version 1.0
 */
public class ReceivedEmailDto {
    /** 이메일 고유 식별자 */
    private int emailId;
    
    /** 발신자 이름(닉네임) */
    private String senderName;
    
    /** 발신자 이메일 주소 */
    private String senderEmail;
    
    /** 이메일 제목 */
    private String title;
    
    /** 이메일 본문 내용 */
    private String body;
    
    /** 읽음 여부 */
    private boolean isRead;
    
    /** 이메일 발송 일시 */
    private LocalDateTime sentDate;

    /**
     * 기본 생성자
     */
    public ReceivedEmailDto() {}

    /**
     * 이메일 ID 반환
     * 
     * @return 이메일 ID
     */
    public int getEmailId() {
        return emailId;
    }

    /**
     * 이메일 ID 설정
     * 
     * @param emailId 설정할 이메일 ID
     */
    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    /**
     * 발신자 이름 반환
     * 
     * @return 발신자 이름(닉네임)
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * 발신자 이름 설정
     * 
     * @param senderName 설정할 발신자 이름
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * 발신자 이메일 주소 반환
     * 
     * @return 발신자 이메일 주소
     */
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * 발신자 이메일 주소 설정
     * 
     * @param senderEmail 설정할 발신자 이메일 주소
     */
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
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
     * 이메일 본문 내용 반환
     * 
     * @return 이메일 본문 내용
     */
    public String getBody() {
        return body;
    }

    /**
     * 이메일 본문 내용 설정
     * 
     * @param body 설정할 이메일 본문 내용
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 읽음 여부 반환
     * 
     * @return 읽음 여부(true: 읽음, false: 안읽음)
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * 읽음 여부 설정
     * 
     * @param read 설정할 읽음 여부
     */
    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * 발송 일시 반환
     * 
     * @return 이메일 발송 일시
     */
    public LocalDateTime getSentDate() {
        return sentDate;
    }

    /**
     * 발송 일시 설정
     * 
     * @param sentDate 설정할 발송 일시
     */
    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    /**
     * 발송 일시를 포맷팅하여 문자열로 반환하는 유틸리티 메서드
     * 
     * 이메일 발송 일시를 사용자 친화적인 형식(yyyy-MM-dd HH:mm)으로 변환합니다.
     * 
     * @return 포맷팅된 발송 일시, 발송 일시가 없는 경우 빈 문자열
     */
    public String getFormattedDate() {
        if (sentDate == null) {
            return "";
        }
        return sentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}