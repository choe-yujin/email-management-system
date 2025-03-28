package com.metaverse.mail.service.impl.user;

import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dto.user.UserLoginDto;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.UserService;

import java.sql.Connection;

public class UserServiceImpl implements UserService {

    // UserDao 객체 선언 (UserDaoImpl을 사용하여 데이터베이스 작업을 수행)
    private final UserDaoImpl userDao;

    /*
     * UserServiceImpl 객체가 생성될 때, 데이터베이스 연결을 받아 UserDaoImpl을 초기화
     */
    public UserServiceImpl(Connection connection) {
        this.userDao = new UserDaoImpl(connection);  // 데이터베이스 연결 객체를 사용하여 UserDao 초기화
    }

    /*
     * 로그인 처리
     * 사용자가 입력한 이메일과 비밀번호를 검증하여 인증된 사용자를 반환
     * 이메일로 해당 사용자를 데이터베이스에서 조회 후 비밀번호를 확인하고 일치하면 해당 사용자 정보를 반환
     * 로그인 실패 시 null 반환
     */
    @Override
    public User login(UserLoginDto loginDto) {
        // UserLoginDto에서 이메일과 비밀번호 추출
        String email = loginDto.getEmailId();
        String password = loginDto.getPassword();

        // 1. 이메일로 사용자 검색
        User user = userDao.findByEmailId(email);  // UserDao의 findByEmailId 메서드를 사용하여 사용자 조회

        // 사용자가 존재하지 않으면 null 반환
        if (user == null) {
            return null;
        }

        // 2. 비밀번호 검증
        // 입력된 비밀번호와 데이터베이스에 저장된 비밀번호를 비교하여 일치 여부 확인
        if (password.equals(user.getEmailPwd())) {
            // 비밀번호가 일치하면 로그인 성공, 사용자 객체 반환
            return user;
        } else {
            // 비밀번호가 틀리면 로그인 실패, null 반환
            return null;
        }
    }

}
