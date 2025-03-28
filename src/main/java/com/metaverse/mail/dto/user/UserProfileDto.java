package com.metaverse.mail.dto.user;

/**
 * 사용자 프로필 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 사용자 프로필 수정 화면에서 입력한 닉네임, 현재 비밀번호, 새 비밀번호 정보를
 * 서비스 계층으로 전달하는 데 사용됩니다.
 * 
 * DTO 패턴을 사용하여 프레젠테이션 계층과 비즈니스 계층 사이의
 * 데이터 교환을 명확하게 정의합니다.
 * 
 * 담당 개발자: 우선(개발자 A)
 * 
 * @author 유진
 * @version 1.0
 */
public class UserProfileDto {
    /** 사용자 닉네임 */
    private String nickname;
    
    /** 현재 비밀번호 (인증 및 변경 시 필요) */
    private String currentPassword;
    
    /** 새 비밀번호 (변경 시 사용) */
    private String newPassword;

    /**
     * 기본 생성자
     */
    public UserProfileDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param nickname 사용자 닉네임
     * @param currentPassword 현재 비밀번호
     * @param newPassword 새 비밀번호
     */
    public UserProfileDto(String nickname, String currentPassword, String newPassword) {
        this.nickname = nickname;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    /**
     * 닉네임 반환
     * 
     * @return 사용자 닉네임
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 현재 비밀번호 반환
     * 
     * @return 현재 비밀번호
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * 새 비밀번호 반환
     * 
     * @return 새 비밀번호
     */
    public String getNewPassword() {
        return newPassword;
    }

}