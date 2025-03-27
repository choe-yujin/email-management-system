package com.metaverse.mail.view.interfaces.user;

import com.metaverse.mail.dto.user.UserProfileDto;

public interface ProfileView {
    /**
     * 프로필 관리 메뉴 표시
     */
    void showProfileManagement();
    
    /**
     * 사용자 프로필 정보 표시
     * 
     * @param profile 사용자 프로필 정보
     */
    void showProfileInfo(UserProfileDto profile);
    
    /**
     * 프로필 업데이트 폼 표시
     * 
     * @param profile 현재 프로필 정보
     */
    void showProfileUpdateForm(UserProfileDto profile);
    
    /**
     * 프로필 업데이트 결과 표시
     * 
     * @param success 성공 여부
     */
    void showProfileUpdateResult(boolean success);
}
