package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.mail.EmailComposeDto;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.dto.mail.EmailSearchDto;

import java.util.List;

public interface EmailService {
    /**
     * 이메일 발송
     *
     * @param emailDto 이메일 작성 정보
     * @param senderId 발신자 ID
     * @return 성공 여부
     */
    boolean sendEmail(EmailComposeDto emailDto, int senderId);

    /**
     * 받은 이메일 목록 조회
     *
     * @param userId 사용자 ID
     * @return 받은 이메일 목록
     */
    List<ReceivedEmailDto> getReceivedEmails(int userId);

    /**
     * 이메일 상세 정보 조회
     *
     * @param emailId 이메일 ID
     * @param userId  사용자 ID
     * @return 이메일 상세 정보
     */
    ReceivedEmailDto getEmailDetails(int emailId, int userId);

    /**
     * 키워드로 이메일 검색
     *
     * @param keyword 검색 키워드
     * @param userId  사용자 ID
     * @return 검색된 이메일 목록
     */
    List<EmailSearchDto> searchEmails(String keyword, int userId);

    /**
     * 이메일에 답장
     *
     * @param originalEmailId 원본 이메일 ID
     * @param replyContent    답장 내용
     * @param senderId        발신자 ID
     * @return 성공 여부
     */
    boolean replyToEmail(int originalEmailId, String replyContent, int senderId);
}