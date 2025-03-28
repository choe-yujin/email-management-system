package com.metaverse.mail.dao.mock;

import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.model.EmailLink;

import java.util.List;

/**
 * 테스트용 EmailLinkDao Mock 클래스
 */
public class MockEmailLinkDao implements EmailLinkDao {

    @Override
    public boolean createEmailLink(int emailId, int receiverId) {
        // 테스트를 위해 항상 성공 반환
        System.out.println("Mock: 이메일 링크 생성 성공 - 이메일 ID: " + emailId + ", 수신자 ID: " + receiverId);
        return true;
    }

    @Override
    public EmailLink getLinkById(int linkId) {
        return null;
    }

    @Override
    public List<EmailLink> getLinksByReceiverId(int receiverId) {
        return null;
    }

    @Override
    public boolean markAsRead(int linkId) {
        return false;
    }

    @Override
    public boolean markAsDeleted(int linkId) {
        return false;
    }

    @Override
    public boolean restoreEmail(int linkId) {
        return false;
    }
}