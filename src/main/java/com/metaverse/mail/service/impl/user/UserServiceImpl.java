package com.metaverse.mail.service.impl.user;

import com.metaverse.mail.common.Session;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dto.user.UserLoginDto;
import com.metaverse.mail.dto.user.UserProfileDto;
import com.metaverse.mail.dto.user.UserRegisterDto;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao; // UserDao 의존성 선언

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao; // 전달받은 UserDao 객체를 클래스의 필드로 저장
    }

    /*
     * 로그인 처리
     * 사용자가 입력한 이메일과 비밀번호를 통해 로그인 검증을 수행하고, 로그인 성공 시 해당 사용자를 반환
     * 비밀번호가 일치하지 않거나 사용자가 존재하지 않으면 null 반환
     */
    @Override
    public User login(UserLoginDto loginDto) {
        String email = loginDto.getEmailId(); // UserLoginDto에서 이메일 추출
        String password = loginDto.getPassword(); // UserLoginDto에서 비밀번호 추출

        // UserDao의 findByEmailId 메서드를 통해 데이터베이스에서 이메일로 사용자 조회
        User user = userDao.findByEmailId(email);

        if (user == null) { // 사용자가 존재하지 않으면 null 반환
            return null;
        }

        //탈퇴 계정인지 확인
        if (user.getStatus() == 'D') { // 'D(탈퇴 상태)'
            return null; // 탈퇴된 계정은 null을 반환하여 로그인 실패 처리
        }

        // 사용자가 존재하면, 비밀번호 일치 여부를 검증
        if (password.equals(user.getEmailPwd())) {
            return user; // 비밀번호가 맞으면 사용자 객체 반환
        } else {
            return null; // 비밀번호가 틀리면 null 반환
        }
    }

    /*
     * 사용자 등록 처리
     */
    @Override
    public boolean register(UserRegisterDto userDto) {
        // 1. 이메일 중복 체크
        String email = userDto.getEmailId(); // UserRegisterDto에서 이메일 추출
        User existingUser = userDao.findByEmailId(email); // 데이터베이스에서 이메일로 사용자 조회

        if (existingUser != null) { // 이메일이 이미 존재하는 경우
            return false; // 중복된 이메일이므로 회원가입 실패
        }

        // 2. 사용자 객체 생성 (비밀번호는 평문 그대로 사용)
        User newUser = new User();
        newUser.setEmailId(userDto.getEmailId()); // 이메일 설정
        newUser.setEmailPwd(userDto.getPassword()); // 비밀번호 설정
        newUser.setNickname(userDto.getNickname()); // 닉네임 설정
        newUser.setStatus('A'); // 기본 상태를 'A' (활성)로 설정

        // 3. 사용자 정보 저장 (DB에 저장)
        boolean isSaved = userDao.insert(newUser); // DB에 새 사용자 저장

        return isSaved; // 저장 성공 여부 반환
    }

    /*
     * 사용자 프로필 업데이트 처리
     */
    @Override
    public boolean updateProfile(UserProfileDto profileDto,  int userId) {
        // 사용자 조회
        User user = userDao.findById(Session.getInstance().getCurrentUserId());
        if (user == null) {
            return false;  // 사용자가 존재하지 않으면 실패
        }

        // 현재 비밀번호가 일치하는지 확인
        if (!user.getEmailPwd().equals(profileDto.getCurrentPassword())) {
            return false;  // 비밀번호가 맞지 않으면 실패
        }

        // 닉네임 수정
        if (profileDto.getNickname() != null && !profileDto.getNickname().isEmpty()) {
            // 닉네임 수정
            boolean isNicknameUpdated = userDao.updateNickname(userId, profileDto.getNickname());   // updateNickname 호출
            if (!isNicknameUpdated) {
                return false;  // 닉네임 수정 실패
            }
        }

        // 비밀번호 수정
        if (profileDto.getNewPassword() != null && !profileDto.getNewPassword().isEmpty()) {
            // 비밀번호 수정
            boolean isPasswordUpdated = userDao.updatePassword(userId, profileDto.getNewPassword());    // 비밀번호만 수정
            if (!isPasswordUpdated) {
                return false;  // 비밀번호 수정 실패
            }
        }

        return true;  // 프로필 업데이트 성공
    }

    /*
     * 비밀번호 변경 처리
     */
    @Override
    public boolean changePassword(String oldPassword, String newPassword, int userId) {
        // 사용자 조회
        User user = userDao.findById(userId);

        if (user == null) {
            return false;  // 사용자가 존재하지 않으면 실패
        }

        // 기존 비밀번호가 맞는지 확인
        if (!user.getEmailPwd().equals(oldPassword)) {
            return false;  // 비밀번호가 맞지 않으면 실패
        }

        // 비밀번호 변경
        boolean isPasswordUpdated = userDao.updatePassword(userId, newPassword); // 새 비밀번호 업데이트
        return isPasswordUpdated;  // DB에 비밀번호 업데이트
    }

    /*
     * 계정 비활성화 처리
     */
    @Override
    public boolean deactivateAccount(int userId) {
        return userDao.updateStatus(userId, 'D');  // 사용자 상태를 'D'로 변경 (탈퇴 처리)
    }
}