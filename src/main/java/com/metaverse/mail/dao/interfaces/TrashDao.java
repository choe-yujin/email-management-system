package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.Trash;
import java.util.List;

public interface TrashDao {
    /**
     * 휴지통에 이메일 추가
     * @param linkId 이메일 링크 ID
     * @return 성공 여부
     */
    boolean addToTrash(int linkId);

    /**
     * 사용자의 휴지통 이메일 목록 조회
     * @param userId 사용자 ID
     * @return 휴지통 이메일 목록
     */
    List<Trash> getTrashByUserId(int userId);

    /**
     * 휴지통에서 이메일 복구
     * @param trashId 휴지통 ID
     * @return 성공 여부
     */
    boolean restoreEmail(int trashId);

    /**
     * 휴지통에서 이메일 영구 삭제
     * @param trashId 휴지통 ID
     * @return 성공 여부
     */
    boolean permanentlyDelete(int trashId);

    /**
     * 만료된 이메일 자동 삭제 (30일 이상 경과)
     * @return 삭제된 이메일 수
     */
    int deleteExpiredEmails();
}