package com.metaverse.mail.view.interfaces;

import com.metaverse.mail.dto.inbox.TrashEmailDto;

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
}