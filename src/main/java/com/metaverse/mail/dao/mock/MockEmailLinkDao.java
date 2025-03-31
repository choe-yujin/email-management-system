package com.metaverse.mail.dao.mock;

import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.model.EmailLink;

import java.util.ArrayList;
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
        List<EmailLink> emailLinks = new ArrayList<>();

        // 수신자 ID가 1인 경우 (Single link)
        if (receiverId == 1) {
            EmailLink link = new EmailLink();
            link.setLinkIdx(1);
            link.setReceiverId(1);
            link.setEmailIdx(1001);
            link.setIsReaded('Y');
            link.setIsDeleted('N');
            emailLinks.add(link);
        }

        // 수신자 ID가 2인 경우 (Multiple links)
        if (receiverId == 2) {
            EmailLink link1 = new EmailLink();
            link1.setLinkIdx(3);
            link1.setReceiverId(2);
            link1.setEmailIdx(1002);
            link1.setIsReaded('N');
            link1.setIsDeleted('N');
            emailLinks.add(link1);

            EmailLink link2 = new EmailLink();
            link2.setLinkIdx(4);
            link2.setReceiverId(2);
            link2.setEmailIdx(1003);
            link2.setIsReaded('N');
            link2.setIsDeleted('N');
            emailLinks.add(link2);
        }

        return emailLinks;
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