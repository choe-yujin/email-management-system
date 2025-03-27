package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.Trash;
import java.util.List;

/**
 * 휴지통 데이터 액세스를 위한 인터페이스
 * 
 * 이 인터페이스는 휴지통에 있는 이메일 정보에 대한 데이터베이스 CRUD(Create, Read, Update, Delete)
 * 연산을 정의합니다. 각 구현체는 이 인터페이스를 구현하여 데이터베이스와의 실제 통신을 담당합니다.
 * 
 * 데이터 액세스 계층의 일부로, 서비스 계층과 데이터베이스 간의 중간 역할을 합니다.
 * 
 * 담당 개발자: 효민(개발자 C)
 * 
 * @author 유진
 * @version 1.0
 */
public interface TrashDao {
    /**
     * 휴지통에 이메일 추가
     * 
     * 데이터베이스에 삭제된 이메일을 휴지통에 추가합니다.
     * 이메일을 삭제(휴지통으로 이동)할 때 호출됩니다.
     * 
     * 예상 SQL:
     * INSERT INTO TRASH (link_id, deleted_at, expiration_date, is_restored) 
     * VALUES (?, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 'N')
     *
     * @param linkId 이메일 링크 ID
     * @return 성공 여부
     */
    boolean addToTrash(int linkId);

    /**
     * 사용자의 휴지통 이메일 목록 조회
     * 
     * 특정 사용자의 휴지통에 있는 이메일 목록을 데이터베이스에서 조회합니다.
     * 휴지통 화면 표시 시 호출됩니다.
     * 
     * 예상 SQL:
     * SELECT t.* FROM TRASH t 
     * JOIN EMAIL_LINK el ON t.link_id = el.link_idx 
     * WHERE el.receiver_id = ? AND t.is_restored = 'N'
     * ORDER BY t.deleted_at DESC
     *
     * @param userId 사용자 ID
     * @return 휴지통 이메일 목록
     */
    List<Trash> getTrashByUserId(int userId);

    /**
     * 휴지통에서 이메일 복구
     * 
     * 데이터베이스에서 휴지통 항목의 복구 상태를 변경합니다.
     * 휴지통에서 이메일을 복구할 때 호출됩니다.
     * 
     * 예상 SQL:
     * UPDATE TRASH SET is_restored = 'Y' WHERE trash_idx = ?
     *
     * @param trashId 휴지통 ID
     * @return 성공 여부
     */
    boolean restoreEmail(int trashId);

    /**
     * 휴지통에서 이메일 영구 삭제
     * 
     * 데이터베이스에서 휴지통 항목을 영구 삭제합니다.
     * 이메일을 영구적으로 삭제할 때 호출됩니다.
     * 
     * 예상 SQL:
     * DELETE FROM TRASH WHERE trash_idx = ?
     *
     * @param trashId 휴지통 ID
     * @return 성공 여부
     */
    boolean permanentlyDelete(int trashId);

    /**
     * 만료된 이메일 자동 삭제 (30일 이상 경과)
     * 
     * 데이터베이스에서 만료 날짜가 지난 휴지통 항목을 자동으로 삭제합니다.
     * 시스템에서 정기적으로 호출하여 오래된 이메일을 정리합니다.
     * 
     * 예상 SQL:
     * DELETE FROM TRASH WHERE expiration_date < NOW() AND is_restored = 'N'
     *
     * @return 삭제된 이메일 수
     */
    int deleteExpiredEmails();
}