package com.metaverse.mail.service.impl.inbox;

import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.TrashDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dto.inbox.SentEmailDto;
import com.metaverse.mail.model.EmailLink;
import com.metaverse.mail.service.interfaces.InboxService;

import java.util.List;

public class InboxServiceImpl implements InboxService {
    private final EmailDao emailDao;
    private final EmailLinkDao emailLinkDao;
    private final UserDao userDao;
    private final TrashDao trashDao;

    /**
     * 생성자
     *
     * @param emailDao     이메일 DAO
     * @param emailLinkDao 이메일 링크 DAO
     * @param userDao      사용자 DAO
     * @param trashDao     휴지통 DAO
     */
    public InboxServiceImpl(EmailDao emailDao, EmailLinkDao emailLinkDao, UserDao userDao, TrashDao trashDao) {
        this.emailDao = emailDao;
        this.emailLinkDao = emailLinkDao;
        this.userDao = userDao;
        this.trashDao = trashDao;
    }

    @Override
    public List<SentEmailDto> getSentEmails(int userId) {
        // 구현 필요
        return List.of();
    }

    @Override
    public SentEmailDto getSentEmailDetails(int emailId) {
        // 구현 필요
        return null;
    }

    /**
     * 받은 이메일 삭제 (휴지통으로 이동)
     *
     * @param emailId 이메일 ID
     * @param userId  사용자 ID
     * @return 삭제 성공 여부(true: 성공, false: 실패)
     */
    @Override
    public boolean deleteReceivedEmail(int emailId, int userId) {
        // 1. 사용자가 수신한 이메일 링크 찾기
        List<EmailLink> links = emailLinkDao.getLinksByReceiverId(userId);
        EmailLink targetLink = null;

        for (EmailLink link : links) {
            if (link.getEmailIdx() == emailId) {
                targetLink = link;
                break;
            }
        }

        if (targetLink == null) {
            System.err.println("해당 이메일을 찾을 수 없거나 사용자가 수신자가 아닙니다.");
            return false;
        }

        // 2. 이미 삭제된 이메일인지 확인
        if (targetLink.getIsDeleted() == 'Y') {
            System.err.println("이미 삭제된 이메일입니다.");
            return false;
        }

        try {
            // 3. 이메일 링크 삭제 상태로 변경
            int linkId = targetLink.getLinkIdx();
            boolean marked = emailLinkDao.markAsDeleted(linkId);

            if (!marked) {
                System.err.println("이메일 삭제 상태 변경 실패");
                return false;
            }

            // 4. 휴지통에 추가
            boolean addedToTrash = trashDao.addToTrash(linkId);

            if (!addedToTrash) {
                System.err.println("휴지통 항목 추가 실패");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("이메일 삭제 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSentEmail(int emailId) {
        // 구현 필요
        return false;
    }
}