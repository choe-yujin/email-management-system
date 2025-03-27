package com.metaverse.mail.dto.inbox;

import java.time.LocalDateTime;

/**
 * 휴지통에 있는 이메일 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 휴지통 화면에서 표시할 이메일 정보(제목, 발신/수신자, 삭제 일시, 만료 일시 등)를
 * 서비스 계층에서 프레젠테이션 계층으로 전달하는 데 사용됩니다.
 * 
 * DTO 패턴을 사용하여 비즈니스 계층과 프레젠테이션 계층 사이의
 * 데이터 교환을 명확하게 정의합니다.
 * 
 * 담당 개발자: 효민(개발자 C)
 * 
 * @author 유진
 * @version 1.0
 */
public class TrashEmailDto {
    /** 휴지통 항목 고유 식별자 */
    private int trashId;
    
    /** 이메일 고유 식별자 */
    private int emailId;
    
    /** 이메일 제목 */
    private String title;
    
    /** 상대방 이름 (발신자 또는 수신자) */
    private String personName;
    
    /** 이메일 유형 ("수신" 또는 "발신") */
    private String emailType;
    
    /** 이메일 삭제 일시 */
    private LocalDateTime deletedDate;
    
    /** 이메일 만료 일시 (영구 삭제 예정 일시) */
    private LocalDateTime expirationDate;

    /**
     * 기본 생성자
     */
    public TrashEmailDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param trashId 휴지통 항목 ID
     * @param emailId 이메일 ID
     * @param title 이메일 제목
     * @param personName 상대방 이름 (발신자 또는 수신자)
     * @param emailType 이메일 유형 ("수신" 또는 "발신")
     * @param deletedDate 이메일 삭제 일시
     * @param expirationDate 이메일 만료 일시
     */
    public TrashEmailDto(int trashId, int emailId, String title, String personName, String emailType, LocalDateTime deletedDate, LocalDateTime expirationDate) {
        this.trashId = trashId;
        this.emailId = emailId;
        this.title = title;
        this.personName = personName;
        this.emailType = emailType;
        this.deletedDate = deletedDate;
        this.expirationDate = expirationDate;
    }

    /**
     * 휴지통 항목 ID 반환
     * 
     * @return 휴지통 항목 ID
     */
    public int getTrashId() {
        return trashId;
    }

    /**
     * 휴지통 항목 ID 설정
     * 
     * @param trashId 설정할 휴지통 항목 ID
     */
    public void setTrashId(int trashId) {
        this.trashId = trashId;
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
     * 삭제 일시 반환
     * 
     * @return 이메일 삭제 일시
     */
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    /**
     * 삭제 일시 설정
     * 
     * @param deletedDate 설정할 삭제 일시
     */
    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * 만료 일시 반환
     * 
     * @return 이메일 만료 일시
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
}