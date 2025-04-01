package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;

/**
 * 수신 이메일 검색 결과 정보를 전달하기 위한 DTO 클래스
 *
 * 이 클래스는 수신 이메일 검색 결과 화면에 표시할 정보
 * (이메일 ID, 제목, 발신자, 읽음 상태 등)를 서비스 계층에서
 * 프레젠테이션 계층으로 전달하는 데 사용됩니다.
 *
 * EmailSearchDto를 상속하여 수신 이메일 관련 추가 정보를 포함합니다.
 *
 * @author 유진
 * @version 1.0
 */
public class ReceivedEmailSearchDto extends EmailSearchDto {
    /** 발신자 이름 */
    private String senderName;

    /** 발신자 이메일 */
    private String senderEmail;

    /** 읽음 여부 */
    private boolean isRead;

    /**
     * 기본 생성자
     */
    public ReceivedEmailSearchDto() {
        super();
    }

    /**
     * 모든 필드를 초기화하는 생성자
     *
     * @param emailId 이메일 ID
     * @param title 이메일 제목
     * @param senderName 발신자 이름
     * @param senderEmail 발신자 이메일
     * @param isRead 읽음 여부
     * @param sentDate 발송 일시
     */
    public ReceivedEmailSearchDto(int emailId, String title, String senderName,
                                  String senderEmail, boolean isRead, LocalDateTime sentDate) {
        super(emailId, title, sentDate);
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.isRead = isRead;
    }

    /**
     * 발신자 이름 반환
     *
     * @return 발신자 이름
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * 발신자 이름 설정
     *
     * @param senderName 발신자 이름
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * 발신자 이메일 반환
     *
     * @return 발신자 이메일
     */
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * 발신자 이메일 설정
     *
     * @param senderEmail 발신자 이메일
     */
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
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
     * @param isRead 읽음 여부
     */
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}