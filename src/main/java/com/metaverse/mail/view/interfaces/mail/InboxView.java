package com.metaverse.mail.view.interfaces.mail;

import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import java.util.List;

public interface InboxView {
    /**
     * 받은 메일함 표시
     */
    void showInbox();
    
    /**
     * 메일 목록 표시
     * 
     * @param emails 메일 목록
     */
    void showEmailList(List<ReceivedEmailDto> emails);
    
    /**
     * 메일 상세 내용 표시
     *
     * @param email 이메일 DTO
     */
    void showEmailDetail(ReceivedEmailDto email);

    /**
     * 이메일 ID로 메일 상세 내용 표시
     *
     * @param emailId 이메일 ID
     */
    void showEmailDetails(int emailId);

    /**
     * 이메일 읽음 표시 결과
     * 
     * @param emailId 이메일 ID
     * @param success 성공 여부
     */
    void showMarkAsReadResult(long emailId, boolean success);
}
