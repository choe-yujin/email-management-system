package com.metaverse.mail.view.interfaces.mail;

import com.metaverse.mail.dto.mail.EmailSearchDto;
import com.metaverse.mail.dto.mail.ReceivedEmailSearchDto;
import com.metaverse.mail.dto.mail.SentEmailSearchDto;

import java.util.List;

public interface SearchView {
    /**
     * 메일 검색 폼 표시
     */
    void showSearchForm();

    // 수신메일 검색 결과 표시를 위한 메서드
    void showReceivedEmailSearchResults(String keyword, List<ReceivedEmailSearchDto> results);

    // 발신메일 검색 결과 표시를 위한 메서드
    void showSentEmailSearchResults(String keyword, List<SentEmailSearchDto> results);

    /**
     * 특정 이메일 상세 정보 표시
     *
     * @param emailId 이메일 ID
     * @param emailType 이메일 유형 ("수신" 또는 "발신")
     */
    void showEmailDetails(int emailId, String emailType);

    /**
     * 검색 결과가 없을 때 표시
     *
     * @param keyword 검색어
     */
    void showNoSearchResults(String keyword);
}