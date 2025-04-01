package com.metaverse.mail.view.interfaces.mail;

import com.metaverse.mail.dto.mail.ReceivedEmailDto;

/**
 * 메일 답장 화면 인터페이스
 *
 * @author 유진
 * @version 1.0
 */
public interface ReplyView {

    /**
     * 답장 작성 화면 표시
     *
     * @param originalEmail 원본 이메일 정보
     */
    void showReplyForm(ReceivedEmailDto originalEmail);
}