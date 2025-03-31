package com.metaverse.mail.service.impl.mail;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.impl.inbox.EmailLinkDaoImpl;
import com.metaverse.mail.dao.impl.mail.EmailDaoImpl;
import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dto.mail.ReceivedEmailDto;
import com.metaverse.mail.model.EmailLink;
import com.metaverse.mail.service.interfaces.EmailService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceImplTest {

    private static Connection connection;
    private EmailService emailService;
    private EmailDao emailDao;
    private EmailLinkDao emailLinkDao;
    private UserDao userDao;

    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("이메일 상세 조회 기능 테스트 시작");
        connection = JDBCConnection.getConnection();
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("이메일 상세 조회 기능 테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        // 실제 DAO 및 Service 객체 생성
        emailDao = new EmailDaoImpl(connection);
        emailLinkDao = new EmailLinkDaoImpl(connection);
        userDao = new UserDaoImpl(connection);
        emailService = new EmailServiceImpl(emailDao, emailLinkDao, userDao);
    }

    @Test
    void testGetEmailDetails_ValidEmailAndUser() {
        // 테스트 데이터 - 실제 DB에 존재하는 이메일 ID와 사용자 ID 사용
        // sample_data.sql의 데이터를 기반으로 테스트
        int emailId = 1; // 첫 번째 이메일 (회의 일정 공지)
        int userId = 2; // 박영희 (수신자 중 한 명)

        // 이메일 상세 정보 조회
        ReceivedEmailDto emailDetails = emailService.getEmailDetails(emailId, userId);

        // 결과 검증
        assertNotNull(emailDetails, "이메일 상세 정보는 null이 아니어야 합니다.");
        assertEquals(emailId, emailDetails.getEmailId(), "이메일 ID가 일치해야 합니다.");
        assertEquals("김철수", emailDetails.getSenderName(), "발신자 이름이 일치해야 합니다.");
        assertEquals("kim@example.com", emailDetails.getSenderEmail(), "발신자 이메일이 일치해야 합니다.");
        assertEquals("회의 일정 공지", emailDetails.getTitle(), "이메일 제목이 일치해야 합니다.");
        assertTrue(emailDetails.isRead(), "이메일이 읽음 상태로 표시되어야 합니다.");

        // 출력 테스트
        System.out.println("이메일 상세 조회 결과:");
        System.out.println("발신자: " + emailDetails.getSenderName() + " (" + emailDetails.getSenderEmail() + ")");
        System.out.println("제목: " + emailDetails.getTitle());
        System.out.println("내용: " + emailDetails.getBody());
        System.out.println("발송일: " + emailDetails.getFormattedDate());
        System.out.println("읽음 상태: " + emailDetails.isRead());
    }

    @Test
    void testGetEmailDetails_InvalidEmail() {
        // 존재하지 않는 이메일 ID
        int invalidEmailId = 9999;
        int userId = 1;

        // 이메일 상세 정보 조회
        ReceivedEmailDto emailDetails = emailService.getEmailDetails(invalidEmailId, userId);

        // 결과 검증
        assertNull(emailDetails, "존재하지 않는 이메일 ID의 경우 null이 반환되어야 합니다.");
    }

    @Test
    void testGetEmailDetails_UnauthorizedUser() {
        // 실제 존재하는 이메일이지만, 해당 사용자는 수신자가 아님
        int emailId = 1; // 첫 번째 이메일 (회의 일정 공지)
        int unauthorizedUserId = 7; // 정영수 (이 이메일의 수신자가 아님)

        // 이메일 상세 정보 조회
        ReceivedEmailDto emailDetails = emailService.getEmailDetails(emailId, unauthorizedUserId);

        // 결과 검증
        assertNull(emailDetails, "사용자가 수신자가 아닌 경우 null이 반환되어야 합니다.");
    }

    @Test
    void testMarkAsRead_Success() {
        // 테스트 데이터 - 읽지 않은 이메일 링크
        // sample_data.sql의 데이터 중 아직 읽지 않은 이메일 링크 선택
        int linkId = 3; // 이민수가 '회의 일정 공지'를 읽지 않은 링크

        // 읽음 상태로 변경
        boolean result = emailLinkDao.markAsRead(linkId);

        // 결과 검증
        assertTrue(result, "읽음 상태 변경이 성공해야 합니다.");

        // 변경된 링크 다시 조회하여 확인
        EmailLink updatedLink = emailLinkDao.getLinkById(linkId);
        assertNotNull(updatedLink, "업데이트된 링크는 null이 아니어야 합니다.");
        assertEquals('Y', updatedLink.getIsReaded(), "이메일이 읽음 상태로 변경되어야 합니다.");
    }
}