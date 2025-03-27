package com.metaverse.mail.dao.interfaces;

import com.metaverse.mail.model.EmailLink;
import java.util.List;

public interface EmailLinkDao {
    /**
     * 이메일 링크 생성 (이메일-수신자 연결)
     * @param emailId 이메일 ID
     * @param receiverId 수신자 ID
     * @return 성공 여부
     */
    boolean createEmailLink(int emailId, int receiverId);

    /**
     * 링크 ID로 이메일 링크 조회
     * @param linkId 링크 ID
     * @return 이메일 링크 객체, 없으면 null
     */
    EmailLink getLinkById(int linkId);

    /**
     * 수신자 ID로 받은 이메일 링크 목록 조회
     * @param receiverId 수신자 ID
     * @return 이메일 링크 목록
     */
    List<EmailLink> getLinksByReceiverId(int receiverId);

    /**
     * 이메일을 읽음 상태로 변경
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    boolean markAsRead(int linkId);

    /**
     * 이메일을 삭제 상태로 변경
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    boolean markAsDeleted(int linkId);

    /**
     * 이메일을 복구 (삭제 취소)
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    boolean restoreEmail(int linkId);
}