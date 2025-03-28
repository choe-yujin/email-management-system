package com.metaverse.mail.view.interfaces.mail;

/**
 * 메일 작성 화면 인터페이스
 *
 * 사용자가 이메일을 작성하고 전송하는 UI를 정의합니다.
 *
 * @author 유진
 * @version 1.0
 */
public interface ComposeView {

    /**
     * 이메일 작성 폼을 표시하고 사용자 입력을 처리합니다.
     * 수신자, 제목, 내용을 입력받아 이메일을 전송합니다.
     */
    void showComposeForm();
}