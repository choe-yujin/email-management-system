package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.EmailLink;
import java.util.List;

/**
 * 이메일 링크(이메일-수신자 연결 정보) 데이터 액세스를 위한 인터페이스
 * 
 * 이 인터페이스는 이메일과 수신자 간의 연결 정보에 대한 데이터베이스 CRUD(Create, Read, Update, Delete)
 * 연산을 정의합니다. 각 구현체는 이 인터페이스를 구현하여 데이터베이스와의 실제 통신을 담당합니다.
 * 
 * 데이터 액세스 계층의 일부로, 서비스 계층과 데이터베이스 간의 중간 역할을 합니다.
 * 
 * 담당 개발자: 효민(개발자 C)
 * 
 * @author 유진
 * @version 1.0
 */
public interface EmailLinkDao {
    /**
     * 이메일 링크 생성 (이메일-수신자 연결)
     * 
     * 데이터베이스에 이메일과 수신자 간의 연결 정보를 저장합니다.
     * 이메일 발송 시 각 수신자마다 호출됩니다.
     * 
     * 예상 SQL:
     * INSERT INTO EMAIL_LINK (receiver_id, email_idx, is_readed, is_deleted) 
     * VALUES (?, ?, 'N', 'N')
     *
     * @param emailId 이메일 ID
     * @param receiverId 수신자 ID
     * @return 성공 여부
     */
    boolean createEmailLink(int emailId, int receiverId);

    /**
     * 링크 ID로 이메일 링크 조회
     * 
     * 데이터베이스에서 지정된 링크 ID에 해당하는 이메일 링크 정보를 검색합니다.
     * 
     * 예상 SQL:
     * SELECT * FROM EMAIL_LINK WHERE link_idx = ?
     *
     * @param linkId 링크 ID
     * @return 이메일 링크 객체, 없으면 null
     */
    EmailLink getLinkById(int linkId);

    /**
     * 수신자 ID로 받은 이메일 링크 목록 조회
     * 
     * 특정 사용자가 받은 모든 이메일 링크 목록을 데이터베이스에서 조회합니다.
     * 받은 메일함 기능 구현 시 호출됩니다.
     * 
     * 예상 SQL:
     * SELECT * FROM EMAIL_LINK WHERE receiver_id = ? AND is_deleted = 'N' 
     * ORDER BY link_idx DESC
     *
     * @param receiverId 수신자 ID
     * @return 이메일 링크 목록
     */
    List<EmailLink> getLinksByReceiverId(int receiverId);

    /**
     * 이메일을 읽음 상태로 변경
     * 
     * 데이터베이스에서 이메일 링크의 읽음 상태를 변경합니다.
     * 이메일 상세 내용 조회 시 호출됩니다.
     * 
     * 예상 SQL:
     * UPDATE EMAIL_LINK SET is_readed = 'Y' WHERE link_idx = ?
     *
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    boolean markAsRead(int linkId);

    /**
     * 이메일을 삭제 상태로 변경
     * 
     * 데이터베이스에서 이메일 링크의 삭제 상태를 변경합니다.
     * 이메일 삭제(휴지통으로 이동) 시 호출됩니다.
     * 
     * 예상 SQL:
     * UPDATE EMAIL_LINK SET is_deleted = 'Y' WHERE link_idx = ?
     *
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    boolean markAsDeleted(int linkId);

    /**
     * 이메일을 복구 (삭제 취소)
     * 
     * 데이터베이스에서 이메일 링크의 삭제 상태를 정상 상태로 변경합니다.
     * 휴지통에서 이메일 복구 시 호출됩니다.
     * 
     * 예상 SQL:
     * UPDATE EMAIL_LINK SET is_deleted = 'N' WHERE link_idx = ?
     *
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    boolean restoreEmail(int linkId);
}