package com.metaverse.mail.dao.impl.inbox;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dao.mock.MockEmailDao;
import com.metaverse.mail.dao.mock.MockEmailLinkDao;
import com.metaverse.mail.dao.mock.MockUserDao;
import com.metaverse.mail.model.EmailLink;
import com.metaverse.mail.service.impl.mail.EmailServiceImpl;
import com.metaverse.mail.service.interfaces.EmailService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailLinkDaoImplTest {
    private static Connection connection;
    private EmailService emailService;
    private EmailDao emailDao;
    private EmailLinkDao emailLinkDao;
    private UserDao userDao;

    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("이메일 링크 DAO 테스트 시작");
        connection = JDBCConnection.getConnection();
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("이메일 링크 DAO 테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }

    @BeforeEach
    void setUp() {
        // DAO 및 Service 객체 생성
        emailDao = new MockEmailDao();
        emailLinkDao = new MockEmailLinkDao();
        userDao = new MockUserDao();
        emailService = new EmailServiceImpl(emailDao, emailLinkDao, userDao);
    }

    @Test
    void testGetLinksByReceiverId_Success() {
        // MockEmailLinkDao에서 특정 수신자 ID에 대한 이메일 링크 조회
        int testReceiverId = 1;
        List<EmailLink> emailLinks = emailLinkDao.getLinksByReceiverId(testReceiverId);

        // 검증
        assertNotNull(emailLinks, "이메일 링크 목록은 null이 아니어야 합니다.");
        assertFalse(emailLinks.isEmpty(), "이메일 링크 목록은 비어있지 않아야 합니다.");

        // 첫 번째 이메일 링크 검증
        EmailLink firstLink = emailLinks.get(0);
        assertEquals(1, firstLink.getLinkIdx(), "링크 ID가 일치해야 합니다.");
        assertEquals(testReceiverId, firstLink.getReceiverId(), "수신자 ID가 일치해야 합니다.");
        assertEquals(1001, firstLink.getEmailIdx(), "이메일 ID가 일치해야 합니다.");
        assertEquals('Y', firstLink.getIsReaded(), "읽음 상태가 일치해야 합니다.");
        assertEquals('N', firstLink.getIsDeleted(), "삭제 상태가 일치해야 합니다.");
    }

    @Test
    void testGetLinksByReceiverId_NoResults() {
        // 존재하지 않는 수신자 ID로 조회
        int nonExistentReceiverId = 9999;
        List<EmailLink> emailLinks = emailLinkDao.getLinksByReceiverId(nonExistentReceiverId);

        // 검증
        assertNotNull(emailLinks, "이메일 링크 목록은 null이 아니어야 합니다.");
        assertTrue(emailLinks.isEmpty(), "존재하지 않는 수신자 ID의 경우 빈 목록이 반환되어야 합니다.");
    }

    @Test
    void testGetLinksByReceiverId_MultipleLinks() {
        // 여러 개의 이메일 링크를 가진 수신자 ID로 조회
        int testReceiverId = 2;
        List<EmailLink> emailLinks = emailLinkDao.getLinksByReceiverId(testReceiverId);

        // 검증
        assertNotNull(emailLinks, "이메일 링크 목록은 null이 아니어야 합니다.");
        assertEquals(2, emailLinks.size(), "2개의 이메일 링크가 반환되어야 합니다.");

        // 첫 번째 이메일 링크 검증
        EmailLink firstLink = emailLinks.get(0);
        assertEquals(3, firstLink.getLinkIdx(), "첫 번째 링크 ID가 일치해야 합니다.");
        assertEquals(testReceiverId, firstLink.getReceiverId(), "첫 번째 수신자 ID가 일치해야 합니다.");
        assertEquals(1002, firstLink.getEmailIdx(), "첫 번째 이메일 ID가 일치해야 합니다.");
        assertEquals('N', firstLink.getIsReaded(), "첫 번째 읽음 상태가 일치해야 합니다.");
        assertEquals('N', firstLink.getIsDeleted(), "첫 번째 삭제 상태가 일치해야 합니다.");

        // 두 번째 이메일 링크 검증
        EmailLink secondLink = emailLinks.get(1);
        assertEquals(4, secondLink.getLinkIdx(), "두 번째 링크 ID가 일치해야 합니다.");
        assertEquals(testReceiverId, secondLink.getReceiverId(), "두 번째 수신자 ID가 일치해야 합니다.");
        assertEquals(1003, secondLink.getEmailIdx(), "두 번째 이메일 ID가 일치해야 합니다.");
        assertEquals('N', secondLink.getIsReaded(), "두 번째 읽음 상태가 일치해야 합니다.");
        assertEquals('N', secondLink.getIsDeleted(), "두 번째 삭제 상태가 일치해야 합니다.");
    }
}