package com.metaverse.mail.dao.mock;

import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.model.User;

/**
 * 테스트용 UserDao Mock 클래스
 */
public class MockUserDao implements UserDao {


    @Override
    public User findById(int userId) {
        return null;
    }

    @Override
    public User findByEmailId(String emailId) {
        // 테스트용 더미 사용자 반환
        User user = new User();
        user.setIdx(1);
        user.setEmailId(emailId);
        user.setNickname("테스트사용자");
        user.setStatus('A');
        return user;
    }

    @Override
    public boolean insert(User user) {
        return false;
    }

    @Override
    public boolean updateNickname(int userIdx, String nickname) {
        return false;
    }
    @Override
    public boolean updatePassword(int userIdx, String nickname) {
        return false;
    }

    @Override
    public boolean updateStatus(int userId, char status) {
        return false;
    }

}