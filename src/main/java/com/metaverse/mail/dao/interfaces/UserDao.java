package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.User;
import java.util.List;

/**
 * 사용자 데이터 액세스를 위한 인터페이스
 * 
 * 이 인터페이스는 사용자 정보에 대한 데이터베이스 CRUD(Create, Read, Update, Delete)
 * 연산을 정의합니다. 각 구현체는 이 인터페이스를 구현하여 데이터베이스와의 실제 통신을 담당합니다.
 * 
 * 데이터 액세스 계층의 일부로, 서비스 계층과 데이터베이스 간의 중간 역할을 합니다.
 * 
 * 담당 개발자: 우선(개발자 A)
 * 
 * @author 유진
 * @version 1.0
 */
public interface UserDao {
    /**
     * 사용자 ID로 사용자 정보 조회
     * 
     * 데이터베이스에서 지정된 ID에 해당하는 사용자 정보를 검색합니다.
     *
     *
     * @param userId 조회할 사용자 ID
     * @return 조회된 사용자 객체, 없으면 null
     */
    User findById(int userId);

    /**
     * 이메일 주소로 사용자 정보 조회
     * 
     * 데이터베이스에서 지정된 이메일 주소에 해당하는 사용자 정보를 검색합니다.
     * 주로 로그인 시 사용자 인증에 활용됩니다.
     *
     *
     * @param emailId 조회할 이메일 주소
     * @return 조회된 사용자 객체, 없으면 null
     */
    User findByEmailId(String emailId);

    /**
     * 새 사용자 추가
     * 
     * 데이터베이스에 새로운 사용자 정보를 추가합니다.
     * 회원가입 처리 시 호출됩니다.
     *
     * @param user 추가할 사용자 객체
     * @return 추가 성공 여부(true: 성공, false: 실패)
     */
    boolean insert(User user);

    /**
     * 사용자 정보 수정
     *
     * 데이터베이스에 저장된 사용자 정보를 업데이트합니다.
     * 프로필 수정이나 비밀번호 변경 시 호출됩니다.
     *
     * @param nickname 수정할 사용자 닉네임, newPassword 새로 변경할 사용자 비밀번호
     * @return 수정 성공 여부(true: 성공, false: 실패)
     */
    boolean updateNickname(int userIdx, String nickname);
    boolean updatePassword(int userIdx, String newPassword);

    /**
     * 사용자 상태 변경
     * 
     * 데이터베이스에 저장된 사용자의 상태를 변경합니다.
     * 회원 탈퇴나 계정 재활성화 시 호출됩니다.
     *
     * @param userId 상태를 변경할 사용자 ID
     * @param status 변경할 상태 ('A': 활성, 'D': 탈퇴)
     * @return 상태 변경 성공 여부(true: 성공, false: 실패)
     */
    boolean updateStatus(int userId, char status);
}