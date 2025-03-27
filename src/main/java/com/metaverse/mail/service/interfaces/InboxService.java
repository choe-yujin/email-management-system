package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.inbox.SentEmailDto;

import java.util.List;

public interface InboxService {
    /**
     * 보낸 이메일 목록 조회
     *
     * @param userId 사용자 ID
     * @return 보낸 이메일 목록
     */
    List<SentEmailDto> getSentEmails(int userId);

    /**
     * 보낸 이메일 상세 정보 조회
     *
     * @param emailId 이메일 ID
     * @return 이메일 상세 정보
     */
    SentEmailDto getSentEmailDetails(int emailId);

    /**
     * 받은 이메일 삭제 (휴지통으로 이동)
     *
     * @param emailId 이메일 ID
     * @param userId  사용자 ID
     * @return 성공 여부
     */
    boolean deleteReceivedEmail(int emailId, int userId);

    /**
     * 보낸 이메일 삭제 (휴지통으로 이동)
     *
     * @param emailId 이메일 ID
     * @return 성공 여부
     */
    boolean deleteSentEmail(int emailId);
}