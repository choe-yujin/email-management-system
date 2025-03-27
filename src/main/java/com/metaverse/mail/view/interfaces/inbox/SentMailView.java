package com.metaverse.mail.view.interfaces.inbox;

import com.metaverse.mail.dto.inbox.SentEmailDto;
import java.util.List;

public interface SentMailView {
    /**
     * 보낸 메일함 목록 표시
     */
    void showSentMailList();
    
    /**
     * 보낸 메일 상세 표시
     * 
     * @param email 보낸 메일 DTO
     */
    void showSentMailDetail(SentEmailDto email);
    
    /**
     * 보낸 메일 목록 표시
     * 
     * @param emails 보낸 메일 목록
     */
    void displaySentEmails(List<SentEmailDto> emails);
    
    /**
     * 보낸 메일 삭제 결과 표시
     * 
     * @param emailId 메일 ID
     * @param success 성공 여부
     */
    void showDeleteResult(long emailId, boolean success);
}
