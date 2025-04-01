package com.metaverse.mail.dto.mail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 발신 이메일 검색 결과 정보를 전달하기 위한 DTO 클래스
 *
 * 이 클래스는 발신 이메일 검색 결과 화면에 표시할 정보
 * (이메일 ID, 제목, 수신자 목록 등)를 서비스 계층에서
 * 프레젠테이션 계층으로 전달하는 데 사용됩니다.
 *
 * EmailSearchDto를 상속하여 발신 이메일 관련 추가 정보를 포함합니다.
 *
 * @author 유진
 * @version 1.0
 */
public class SentEmailSearchDto extends EmailSearchDto {
    /** 수신자 이름 목록 */
    private List<String> receiverNames;

    /** 수신자 이메일 목록 */
    private List<String> receiverEmails;

    /** 대표 수신자 이름 (목록의 첫 번째 수신자 또는 여러 명일 경우 요약) */
    private String representativeReceiverName;

    /**
     * 기본 생성자
     */
    public SentEmailSearchDto() {
        super();
        this.receiverNames = new ArrayList<>();
        this.receiverEmails = new ArrayList<>();
    }

    /**
     * 기본 필드를 초기화하는 생성자
     *
     * @param emailId 이메일 ID
     * @param title 이메일 제목
     * @param sentDate 발송 일시
     */
    public SentEmailSearchDto(int emailId, String title, LocalDateTime sentDate) {
        super(emailId, title, sentDate);
        this.receiverNames = new ArrayList<>();
        this.receiverEmails = new ArrayList<>();
    }

    /**
     * 모든 필드를 초기화하는 생성자
     *
     * @param emailId 이메일 ID
     * @param title 이메일 제목
     * @param receiverNames 수신자 이름 목록
     * @param receiverEmails 수신자 이메일 목록
     * @param sentDate 발송 일시
     */
    public SentEmailSearchDto(int emailId, String title, List<String> receiverNames,
                              List<String> receiverEmails, LocalDateTime sentDate) {
        super(emailId, title, sentDate);
        this.receiverNames = receiverNames;
        this.receiverEmails = receiverEmails;
        updateRepresentativeReceiverName();
    }

    /**
     * 수신자 이름 목록 반환
     *
     * @return 수신자 이름 목록
     */
    public List<String> getReceiverNames() {
        return receiverNames;
    }

    /**
     * 수신자 이름 목록 설정
     *
     * @param receiverNames 수신자 이름 목록
     */
    public void setReceiverNames(List<String> receiverNames) {
        this.receiverNames = receiverNames;
        updateRepresentativeReceiverName();
    }

    /**
     * 수신자 이메일 목록 반환
     *
     * @return 수신자 이메일 목록
     */
    public List<String> getReceiverEmails() {
        return receiverEmails;
    }

    /**
     * 수신자 이메일 목록 설정
     *
     * @param receiverEmails 수신자 이메일 목록
     */
    public void setReceiverEmails(List<String> receiverEmails) {
        this.receiverEmails = receiverEmails;
    }

    /**
     * 대표 수신자 이름 반환
     *
     * @return 대표 수신자 이름
     */
    public String getRepresentativeReceiverName() {
        return representativeReceiverName;
    }

    /**
     * 대표 수신자 이름을 수신자 목록에 따라 업데이트
     */
    private void updateRepresentativeReceiverName() {
        if (receiverNames == null || receiverNames.isEmpty()) {
            this.representativeReceiverName = "";
        } else if (receiverNames.size() == 1) {
            this.representativeReceiverName = receiverNames.get(0);
        } else {
            this.representativeReceiverName = receiverNames.get(0) + " 외 " + (receiverNames.size() - 1) + "명";
        }
    }

    /**
     * 수신자 추가
     *
     * @param name 수신자 이름
     * @param email 수신자 이메일
     */
    public void addReceiver(String name, String email) {
        if (this.receiverNames == null) {
            this.receiverNames = new ArrayList<>();
        }
        if (this.receiverEmails == null) {
            this.receiverEmails = new ArrayList<>();
        }

        this.receiverNames.add(name);
        this.receiverEmails.add(email);
        updateRepresentativeReceiverName();
    }
}