package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.user.UserLoginDto;
import com.metaverse.mail.dto.user.UserProfileDto;
import com.metaverse.mail.dto.user.UserRegisterDto;
import com.metaverse.mail.model.User;

public interface UserService {
    /**
     * 회원가입
     *
     * @param userDto 사용자 등록 정보
     * @return 성공 여부
     */
    boolean register(UserRegisterDto userDto);

    /**
     * 로그인
     *
     * @param loginDto 로그인 정보
     * @return 로그인된 사용자 정보, 실패 시 null
     */
    User login(UserLoginDto loginDto);

    /**
     * 사용자 프로필 정보 수정
     *
     * @param profileDto 수정할 프로필 정보
     * @param userId     사용자 ID
     * @return 성공 여부
     */
    boolean updateProfile(UserProfileDto profileDto, int userId);

    /**
     * 비밀번호 변경
     *
     * @param oldPassword 기존 비밀번호
     * @param newPassword 새 비밀번호
     * @param userId      사용자 ID
     * @return 성공 여부
     */
    boolean changePassword(String oldPassword, String newPassword, int userId);

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return 성공 여부
     */
    boolean deactivateAccount(int userId);
}