package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.Email;
import java.util.List;

public interface EmailDao {
    /**
     * 새 이메일 생성
     * @param email 이메일 객체
     * @return 생성된 이메일 ID, 실패 시 -1
     */
    int createEmail(Email email);

    /**
     * 이메일 ID로 이메일 조회
     * @param emailId 이메일 ID
     * @return 이메일 객체, 없으면 null
     */
    Email getEmailById(int emailId);

    /**
     * 발신자 ID로 보낸 이메일 목록 조회
     * @param senderId 발신자 ID
     * @return 이메일 목록
     */
    List<Email> getEmailsBySenderId(int senderId);

    /**
     * 키워드로 이메일 검색
     * @param keyword 검색 키워드
     * @param userId 사용자 ID (발신/수신 이메일 모두 검색)
     * @return 검색된 이메일 목록
     */
    List<Email> searchEmails(String keyword, int userId);

    /**
     * 이메일 삭제 (실제 삭제가 아닌 상태 변경)
     * @param emailId 이메일 ID
     * @return 성공 여부
     */
    boolean deleteEmail(int emailId);
}