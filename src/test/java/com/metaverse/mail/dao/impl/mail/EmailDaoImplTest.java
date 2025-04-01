package com.metaverse.mail.dao.impl.mail;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dao.impl.inbox.EmailLinkDaoImpl;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.model.Email;
import com.metaverse.mail.service.impl.mail.EmailServiceImpl;
import com.metaverse.mail.service.interfaces.EmailService;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class EmailDaoImplTest {

    private static Connection connection;
    private EmailService emailService;
    private EmailDao emailDao;
    private EmailLinkDao emailLinkDao;
    private UserDao userDao;

    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("이메일 DAO 테스트 시작");
        connection = JDBCConnection.getConnection();
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("이메일 DAO 테스트 종료 및 자원 반납");
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

        // 서비스 객체 생성
        emailService = new EmailServiceImpl(emailDao, emailLinkDao, userDao);
    }

    @Test
    void testCreateEmail() {
        // 테스트용 Email 객체 생성
        Email email = new Email();
        email.setSenderId(1); // 테스트 데이터에 있는 kim@example.com 사용자
        email.setTitle("테스트 제목");
        email.setBody("테스트 내용입니다.");
        email.setStatus('Y');
        email.setCreatedAt(LocalDateTime.now());

        // 이메일 생성 테스트
        int emailId = 0;
        try {
            emailId = emailDao.createEmail(email);
        } catch (Exception e) {
            System.err.println("테스트 실패 원인: " + e.getMessage());
            e.printStackTrace();
            fail("예외 발생: " + e.getMessage());
        }

        assertNotNull(emailId);
        // 결과 검증
        assertTrue(emailId > 0, "이메일 생성 시 유효한 ID를 반환해야 함");
        System.out.println("생성된 이메일 ID: " + emailId);

        // 생성된 이메일 조회 테스트
        Email createdEmail = emailDao.getEmailById(emailId);
        assertNotNull(createdEmail, "생성된 이메일이 조회되어야 함");
        assertEquals(email.getTitle(), createdEmail.getTitle(), "이메일 제목이 일치해야 함");
        assertEquals(email.getBody(), createdEmail.getBody(), "이메일 내용이 일치해야 함");
        assertEquals(email.getSenderId(), createdEmail.getSenderId(), "발신자 ID가 일치해야 함");
    }

    @Test
    void testGetEmailById() {
        // 샘플 데이터의 ID 1번 이메일로 테스트
        int existingEmailId = 1;

        Email email = emailDao.getEmailById(existingEmailId);

        // 결과 검증
        assertNotNull(email, "기존 이메일이 조회되어야 함");
        assertEquals(existingEmailId, email.getEmailIdx(), "이메일 ID가 일치해야 함");
    }

}