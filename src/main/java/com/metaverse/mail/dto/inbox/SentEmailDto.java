package com.metaverse.mail.dto.inbox;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 보낸 이메일 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 보낸 메일함에서 표시할 이메일 정보(수신자 목록, 제목, 내용, 발송 일시 등)를
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
public class SentEmailDto {
    /** 이메일 고유 식별자 */
    private int emailId;
    
    /** 수신자 이름(닉네임) 목록 */
    private List<String> receiverNames;
    
    /** 수신자 이메일 주소 목록 */
    private List<String> receiverEmails;
    
    /** 이메일 제목 */
    private String title;
    
    /** 이메일 본문 내용 */
    private String body;
    
    /** 이메일 발송 일시 */
    private LocalDateTime sentDate;

    /**
     * 기본 생성자
     */
    public SentEmailDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param emailId 이메일 ID
     * @param receiverNames 수신자 이름(닉네임) 목록
     * @param receiverEmails 수신자 이메일 주소 목록
     * @param title 이메일 제목
     * @param body 이메일 본문 내용
     * @param sentDate 이메일 발송 일시
     */
    public SentEmailDto(int emailId, List<String> receiverNames, List<String> receiverEmails, String title, String body, LocalDateTime sentDate) {
        this.emailId = emailId;
        this.receiverNames = receiverNames;
        this.receiverEmails = receiverEmails;
        this.title = title;
        this.body = body;
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
     * 수신자 이름 목록 반환
     * 
     * @return 수신자 이름(닉네임) 목록
     */
    public List<String> getReceiverNames() {
        return receiverNames;
    }

    /**
     * 수신자 이메일 주소 목록 반환
     * 
     * @return 수신자 이메일 주소 목록
     */
    public List<String> getReceiverEmails() {
        return receiverEmails;
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
     * 이메일 본문 내용 반환
     * 
     * @return 이메일 본문 내용
     */
    public String getBody() {
        return body;
    }

    /**
     * 발송 일시 반환
     * 
     * @return 이메일 발송 일시
     */
    public LocalDateTime getSentDate() {
        return sentDate;
    }

}