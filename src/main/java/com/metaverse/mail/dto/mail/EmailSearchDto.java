package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;

/**
 * 이메일 검색 결과 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 이메일 검색 결과 화면에 표시할 정보(이메일 ID, 제목, 발신/수신자, 유형 등)를
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
public class EmailSearchDto {
    /** 이메일 고유 식별자 */
    private int emailId;
    
    /** 이메일 제목 */
    private String title;
    
    /** 상대방 이름 (발신자 또는 수신자) */
    private String personName;
    
    /** 이메일 유형 ("수신" 또는 "발신") */
    private String emailType;
    
    /** 읽음 여부 */
    private boolean isRead;
    
    /** 이메일 발송 일시 */
    private LocalDateTime sentDate;

    /**
     * 기본 생성자
     */
    public EmailSearchDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param emailId 이메일 ID
     * @param title 이메일 제목
     * @param personName 상대방 이름 (발신자 또는 수신자)
     * @param emailType 이메일 유형 ("수신" 또는 "발신")
     * @param isRead 읽음 여부
     * @param sentDate 이메일 발송 일시
     */
    public EmailSearchDto(int emailId, String title, String personName, String emailType, boolean isRead, LocalDateTime sentDate) {
        this.emailId = emailId;
        this.title = title;
        this.personName = personName;
        this.emailType = emailType;
        this.isRead = isRead;
        this.sentDate = sentDate;
    }

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
     * 상대방 이름 반환
     * 
     * @return 상대방 이름 (발신자 또는 수신자)
     */
    public String getPersonName() {
        return personName;
    }
    
    /**
     * 상대방 이름 설정
     * 
     * @param personName 설정할 상대방 이름
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
    /**
     * 이메일 유형 반환
     * 
     * @return 이메일 유형 ("수신" 또는 "발신")
     */
    public String getEmailType() {
        return emailType;
    }
    
    /**
     * 이메일 유형 설정
     * 
     * @param emailType 설정할 이메일 유형
     */
    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }
    
    /**
     * 읽음 여부 반환
     * 
     * @return 읽음 여부
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
        this.isRead = read;
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
}