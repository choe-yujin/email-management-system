package com.metaverse.mail.view.interfaces.mail;

import com.metaverse.mail.dto.mail.ReceivedEmailDto;
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
     * @param emails 검색 결과 리스트
     */
    void showSearchResults(String keyword, List<ReceivedEmailDto> emails);
    
    /**
     * 검색 필터 옵션 표시
     */
    void showSearchFilterOptions();
    
    /**
     * 검색 결과가 없을 때 표시
     * 
     * @param keyword 검색어
     */
    void showNoSearchResults(String keyword);
}
