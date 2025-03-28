package com.metaverse.mail.dto.mail;

import java.util.List;

/**
 * 이메일 작성 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 이메일 작성 화면에서 사용자가 입력한 수신자 목록, 제목, 내용 정보를
 * 서비스 계층으로 전달하는 데 사용됩니다.
 * 
 * DTO 패턴을 사용하여 프레젠테이션 계층과 비즈니스 계층 사이의
 * 데이터 교환을 명확하게 정의합니다.
 * 
 * 담당 개발자: 유진(개발자 B)
 * 
 * @author 유진
 * @version 1.0
 */
public class EmailComposeDto {
    /** 수신자 이메일 주소 목록 */
    private List<String> receiverEmails;
    
    /** 이메일 제목 */
    private String title;
    
    /** 이메일 본문 내용 */
    private String body;

    /**
     * 기본 생성자
     */
    public EmailComposeDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param receiverEmails 수신자 이메일 주소 목록
     * @param title 이메일 제목
     * @param body 이메일 본문 내용
     */
    public EmailComposeDto(List<String> receiverEmails, String title, String body) {
        this.receiverEmails = receiverEmails;
        this.title = title;
        this.body = body;
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

}