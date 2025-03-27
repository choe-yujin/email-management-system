package com.metaverse.mail.view.interfaces.inbox;

import com.metaverse.mail.dto.inbox.TrashEmailDto;
import java.util.List;

public interface TrashView {
    /**
     * 휴지통 목록 표시
     */
    void showTrashList();

    /**
     * 휴지통 이메일 상세 내용 표시
     *
     * @param email 휴지통 이메일 DTO
     */
    void showTrashEmailDetail(TrashEmailDto email);
    
    /**
     * 휴지통 메일 목록 표시
     * 
     * @param emails 휴지통 메일 목록
     */
    void displayTrashEmails(List<TrashEmailDto> emails);
    
    /**
     * 휴지통 메일 복구 결과 표시
     * 
     * @param emailId 메일 ID
     * @param success 성공 여부
     */
    void showRestoreResult(long emailId, boolean success);
    
    /**
     * 휴지통 메일 영구 삭제 결과 표시
     * 
     * @param emailId 메일 ID
     * @param success 성공 여부
     */
    void showPermanentDeleteResult(long emailId, boolean success);
}
