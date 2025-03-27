package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.User;
import java.util.List;

public interface UserDao {
    /**
     * 사용자 ID로 사용자 정보 조회
     * @param userId 사용자 ID
     * @return 사용자 정보, 없으면 null
     */
    User findById(int userId);

    /**
     * 이메일로 사용자 정보 조회
     * @param emailId 이메일 ID
     * @return 사용자 정보, 없으면 null
     */
    User findByEmailId(String emailId);

    /**
     * 새 사용자 추가
     * @param user 사용자 객체
     * @return 성공 여부
     */
    boolean insert(User user);

    /**
     * 사용자 정보 수정
     * @param user 수정할 사용자 정보
     * @return 성공 여부
     */
    boolean update(User user);

    /**
     * 사용자 상태 변경 (활성/비활성)
     * @param userId 사용자 ID
     * @param status 변경할 상태 ('A': 활성, 'D': 삭제)
     * @return 성공 여부
     */
    boolean updateStatus(int userId, char status);
}