package com.metaverse.mail.view.interfaces.mail;

public interface ComposeView {
    /**
     * 메일 작성 폼 표시
     */
    void showComposeForm();
    
    /**
     * 메일 작성 결과 표시
     * 
     * @param success 성공 여부
     * @param message 결과 메시지
     */
    void showComposeResult(boolean success, String message);
    
    /**
     * 다수의 받는 사람 입력 페이지 표시
     */
    void showMultipleRecipientsForm();
    
    /**
     * 파일 첨부 페이지 표시
     */
    void showAttachmentForm();
}
