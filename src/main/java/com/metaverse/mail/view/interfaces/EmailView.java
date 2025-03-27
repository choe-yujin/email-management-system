package com.metaverse.mail.view.interfaces;

import com.metaverse.mail.dto.mail.ReceivedEmailDto;

public interface EmailView {
    /**
     * 메일 작성 폼 표시
     */
    void showComposeForm();

    /**
     * 받은 메일함 표시
     */
    void showInbox();

    /**
     * 메일 상세 내용 표시
     *
     * @param email 이메일 DTO
     */
    void showEmailDetail(ReceivedEmailDto email);

    /**
     * 메일 검색 폼 표시
     */
    void showSearchForm();
}