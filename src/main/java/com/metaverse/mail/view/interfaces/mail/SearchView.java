package com.metaverse.mail.view.interfaces.mail;

import com.metaverse.mail.dto.mail.EmailSearchDto;
import java.util.List;

public interface SearchView {
    /**
     * 메일 검색 폼 표시
     */
    void showSearchForm();

    /**
     * 검색 결과 표시
     *
     * @param keyword 검색어
     * @param results 검색 결과 리스트
     */
    void showSearchResults(String keyword, List<EmailSearchDto> results);

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