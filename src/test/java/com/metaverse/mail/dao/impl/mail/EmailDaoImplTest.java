package com.metaverse.mail.dao.impl.mail;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dao.mock.MockEmailLinkDao;
import com.metaverse.mail.dao.mock.MockUserDao;
import com.metaverse.mail.dto.mail.EmailComposeDto;
import com.metaverse.mail.model.Email;
import com.metaverse.mail.service.impl.mail.EmailServiceImpl;
import com.metaverse.mail.service.interfaces.EmailService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EmailDaoImplTest {

    private static Connection connection;
    private EmailService emailService;
    private EmailDao emailDao;
    private EmailLinkDao emailLinkDao;
    private UserDao userDao;


    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("메일 작성 기능 테스트 시작");
        connection = JDBCConnection.getConnection();
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("메일 작성 기능 테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }

    @BeforeEach
    void setUp() {
        // DAO 및 Service 객체 생성
        emailDao = new EmailDaoImpl();
        emailLinkDao = new MockEmailLinkDao();
        userDao = new MockUserDao();
        emailService = new EmailServiceImpl(emailDao, emailLinkDao, userDao);
    }

    @Test
    void testSendEmail() {
        // 테스트용 DTO 생성
        EmailComposeDto dto = new EmailComposeDto(
                Arrays.asList("test@example.com", "test2@example.com"),
                "테스트 제목",
                "테스트 내용입니다."
        );

        // 메일 발송 테스트
        boolean result = emailService.sendEmail(dto, 1);

        // 결과 검증
        Assertions.assertTrue(result, "메일 발송 성공해야 함");
    }


    @Test
    void testEmailDao() throws SQLException {
        // Email 객체 생성
        Email email = new Email();
        email.setSenderId(1);
        email.setTitle("DAO 테스트 제목");
        email.setBody("DAO 테스트 내용");
        email.setStatus('Y');

        // 실제 DAO 메서드 호출 테스트
        int emailId = emailDao.createEmail(email);

        // 결과 검증
        Assertions.assertTrue(emailId > 0, "이메일 생성 시 유효한 ID를 반환해야 함");

        System.out.println("생성된 이메일 ID: " + emailId);
    }
}