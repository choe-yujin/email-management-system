package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.inbox.TrashEmailDto;

import java.util.List;

public interface TrashService {
    /**
     * 휴지통 이메일 목록 조회
     *
     * @param userId 사용자 ID
     * @return 휴지통 이메일 목록
     */
    List<TrashEmailDto> getTrashEmails(int userId);

    /**
     * 휴지통 이메일 상세 정보 조회
     *
     * @param trashId 휴지통 ID
     * @return 이메일 상세 정보
     */
    TrashEmailDto getTrashEmailDetails(int trashId);

    /**
     * 이메일 복구 (휴지통에서 원래 메일함으로)
     *
     * @param trashId 휴지통 ID
     * @return 성공 여부
     */
    boolean restoreEmail(int trashId);

    /**
     * 이메일 영구 삭제
     *
     * @param trashId 휴지통 ID
     * @return 성공 여부
     */
    boolean permanentlyDeleteEmail(int trashId);

    /**
     * 만료된 이메일 자동 삭제
     *
     * @return 삭제된 이메일 수
     */
    int cleanupExpiredEmails();
}