package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 이메일 검색 결과의 기본 정보를 전달하기 위한 DTO 클래스
 *
 * 이 클래스는 이메일 검색 결과의 공통 정보(이메일 ID, 제목, 날짜)를
 * 서비스 계층에서 프레젠테이션 계층으로 전달하는 데 사용됩니다.
 *
 * 확장 가능한 구조로 설계되어 ReceivedEmailSearchDto와 SentEmailSearchDto의 기본 클래스 역할을 합니다.
 *
 * @author 유진
 * @version 1.1
 */
public class EmailSearchDto {
    /** 이메일 고유 식별자 */
    private int emailId;

    /** 이메일 제목 */
    private String title;

    /** 이메일 발송 일시 */
    private LocalDateTime sentDate;

    /** 날짜 포맷터 */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 기본 생성자
     */
    public EmailSearchDto() {}

    /**
     * 기본 필드를 초기화하는 생성자
     *
     * @param emailId 이메일 ID
     * @param title 이메일 제목
     * @param sentDate 이메일 발송 일시
     */
    public EmailSearchDto(int emailId, String title, LocalDateTime sentDate) {
        this.emailId = emailId;
        this.title = title;
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
     * @param emailId 이메일 ID
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
     * @param title 이메일 제목
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @param sentDate 이메일 발송 일시
     */
    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    /**
     * 발송 일시를 포맷팅하여 문자열로 반환
     *
     * @return 포맷팅된 발송 일시 (yyyy-MM-dd)
     */
    public String getFormattedDate() {
        return sentDate != null ? sentDate.format(formatter) : "";
    }
}