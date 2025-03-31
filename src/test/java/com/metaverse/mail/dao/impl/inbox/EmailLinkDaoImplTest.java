package com.metaverse.mail.dao.impl.inbox;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.impl.mail.EmailDaoImpl;
import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.model.Email;
import com.metaverse.mail.model.EmailLink;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailLinkDaoImplTest {

    private static Connection connection;
    private EmailDao emailDao;
    private EmailLinkDao emailLinkDao;
    private UserDao userDao;

    // 테스트에 사용할 이메일 ID와 수신자 ID
    private static final int TEST_RECEIVER_ID = 2; // 테스트 데이터의 park@example.com 사용자

    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("이메일 링크 DAO 테스트 시작");
        connection = JDBCConnection.getConnection();
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("이메일 링크 DAO 테스트 종료 및 자원 반납");
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCConnection.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        // 실제 DAO 객체 생성
        connection = JDBCConnection.getConnection();
        emailDao = new EmailDaoImpl(connection);
        emailLinkDao = new EmailLinkDaoImpl(connection);
        userDao = new UserDaoImpl(connection);
    }

    @Test
    void testCreateEmailLink() {
        // 테스트를 위한 이메일 생성
        Email email = new Email();
        email.setSenderId(1); // 테스트 데이터에 있는 kim@example.com 사용자
        email.setTitle("링크 테스트 제목");
        email.setBody("링크 테스트 내용입니다.");
        email.setStatus('Y');
        email.setCreatedAt(LocalDateTime.now());

        int emailId = emailDao.createEmail(email);
        assertTrue(emailId > 0, "이메일 생성이 성공해야 함");

        // 이메일 링크 생성 테스트
        boolean linkCreated = emailLinkDao.createEmailLink(emailId, TEST_RECEIVER_ID);

        // 결과 검증
        assertTrue(linkCreated, "이메일 링크 생성이 성공해야 함");
    }

    @Test
    void testGetLinksByReceiverId() {
        // 테스트 대상 수신자가 받은 이메일 링크 조회
        List<EmailLink> emailLinks = emailLinkDao.getLinksByReceiverId(TEST_RECEIVER_ID);

        // 결과 검증
        assertNotNull(emailLinks, "이메일 링크 목록은 null이 아니어야 함");

        // 샘플 데이터에 해당 사용자가 받은 메일이 있다고 가정하고 검증
        // 비어있지 않은지만 검증하고 상세 검증은 생략 (데이터에 따라 달라질 수 있음)
        assertFalse(emailLinks.isEmpty(), "테스트 사용자는 최소 하나 이상의 이메일을 받았어야 함");

        // 각 이메일 링크의 수신자 ID 검증
        for (EmailLink link : emailLinks) {
            assertEquals(TEST_RECEIVER_ID, link.getReceiverId(), "모든 링크의 수신자 ID가 일치해야 함");
        }
    }

    @Test
    void testCreateAndGetEmailLink() {
        // 테스트를 위한 이메일 생성
        Email email = new Email();
        email.setSenderId(1); // kim@example.com
        email.setTitle("통합 테스트 제목");
        email.setBody("통합 테스트 내용입니다.");
        email.setStatus('Y');
        email.setCreatedAt(LocalDateTime.now());

        int emailId = emailDao.createEmail(email);
        assertTrue(emailId > 0, "이메일 생성이 성공해야 함");

        // 이메일 링크 생성
        boolean linkCreated = emailLinkDao.createEmailLink(emailId, TEST_RECEIVER_ID);
        assertTrue(linkCreated, "이메일 링크 생성이 성공해야 함");

        // 생성 후 조회
        List<EmailLink> emailLinks = emailLinkDao.getLinksByReceiverId(TEST_RECEIVER_ID);

        // 결과 검증 - 새로 생성한 링크가 조회되는지 확인
        assertNotNull(emailLinks, "이메일 링크 목록은 null이 아니어야 함");
        assertFalse(emailLinks.isEmpty(), "이메일 링크 목록이 비어있지 않아야 함");

        // 새로 생성한 이메일 링크가 목록에 포함되어 있는지 확인
        boolean foundNewLink = false;
        for (EmailLink link : emailLinks) {
            if (link.getEmailIdx() == emailId && link.getReceiverId() == TEST_RECEIVER_ID) {
                foundNewLink = true;
                assertEquals('N', link.getIsReaded(), "새 이메일은 읽지 않은 상태여야 함");
                assertEquals('N', link.getIsDeleted(), "새 이메일은 삭제되지 않은 상태여야 함");
                break;
            }
        }

        assertTrue(foundNewLink, "새로 생성한 이메일 링크가 조회 결과에 포함되어야 함");
    }

}