package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.Email;
import java.util.List;

/**
 * 이메일 데이터 액세스를 위한 인터페이스
 * 
 * 이 인터페이스는 이메일 정보에 대한 데이터베이스 CRUD(Create, Read, Update, Delete)
 * 연산을 정의합니다. 각 구현체는 이 인터페이스를 구현하여 데이터베이스와의 실제 통신을 담당합니다.
 * 
 * 데이터 액세스 계층의 일부로, 서비스 계층과 데이터베이스 간의 중간 역할을 합니다.
 * 
 * 담당 개발자: 유진(개발자 B)
 * 
 * @author 유진
 * @version 1.0
 */
public interface EmailDao {
    /**
     * 새 이메일 생성
     * 
     * 데이터베이스에 새로운 이메일을 저장합니다.
     * 이메일 작성 및 전송 시 호출됩니다.
     *
     * @param email 이메일 객체
     * @return 생성된 이메일 ID, 실패 시 -1
     */
    int createEmail(Email email);

    /**
     * 이메일 ID로 이메일 조회
     * 
     * 데이터베이스에서 지정된 ID에 해당하는 이메일 정보를 검색합니다.
     * 이메일 상세 보기 시 호출됩니다.
     *
     * @param emailId 이메일 ID
     * @return 이메일 객체, 없으면 null
     */
    Email getEmailById(int emailId);

    /**
     * 발신자 ID로 보낸 이메일 목록 조회
     * 
     * 특정 사용자가 보낸 이메일 목록을 데이터베이스에서 조회합니다.
     * 보낸 메일함 기능 구현 시 호출됩니다.
     *
     * @param senderId 발신자 ID
     * @return 이메일 목록
     */
    List<Email> getEmailsBySenderId(int senderId);

    // 수신 이메일 검색 전용 메서드
    List<Email> searchReceivedEmails(String keyword, int receiverId);

    // 추후 개발을 위한 발신 이메일 검색 메서드
    List<Email> searchSentEmails(String keyword, int senderId);

    /**
     * 이메일 삭제 (실제 삭제가 아닌 상태 변경)
     * 
     * 데이터베이스에서 이메일의 상태를 변경하여 삭제 처리합니다.
     * 실제로 데이터베이스에서 레코드를 삭제하지 않고 상태만 변경합니다.
     *
     * @param emailId 이메일 ID
     * @return 성공 여부
     */
    boolean deleteEmail(int emailId);
}