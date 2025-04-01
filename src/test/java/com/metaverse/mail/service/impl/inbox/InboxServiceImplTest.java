package com.metaverse.mail.service.impl.inbox;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.dao.impl.inbox.EmailLinkDaoImpl;
import com.metaverse.mail.dao.impl.inbox.TrashDaoImpl;
import com.metaverse.mail.dao.impl.mail.EmailDaoImpl;
import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.dao.interfaces.TrashDao;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.model.EmailLink;
import com.metaverse.mail.service.interfaces.InboxService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * InboxServiceImpl 클래스의 이메일 삭제 기능을 테스트하는 클래스
 */
class InboxServiceImplTest {
    private static Connection connection;
    private InboxService inboxService;
    private EmailDao emailDao;
    private EmailLinkDao emailLinkDao;
    private UserDao userDao;
    private TrashDao trashDao;

    // 테스트에 사용할 사용자 ID와 이메일 ID
    private static final int TEST_USER_ID = 2; // 샘플 데이터의 park@example.com
    private static int TEST_EMAIL_ID; // 테스트 전에 설정

    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("이메일 삭제 기능 테스트 시작");
        connection = JDBCConnection.getConnection();

        // 테스트 이메일 ID 조회 - park@example.com이 받은 이메일 중 첫 번째 것
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT el.email_idx FROM EMAIL_LINK el " +
                        "WHERE el.receiver_id = ? AND el.is_deleted = 'N' " +
                        "LIMIT 1")) {
            ps.setInt(1, TEST_USER_ID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TEST_EMAIL_ID = rs.getInt("email_idx");
                    System.out.println("테스트에 사용할 이메일 ID: " + TEST_EMAIL_ID);
                } else {
                    throw new RuntimeException("테스트에 사용할 이메일이 없습니다.");
                }
            }
        }
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("이메일 삭제 기능 테스트 종료 및 자원 반납");
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
        // DAO 및 서비스 객체 생성
        connection = JDBCConnection.getConnection();
        emailDao = new EmailDaoImpl(connection);
        emailLinkDao = new EmailLinkDaoImpl(connection);
        userDao = new UserDaoImpl(connection);
        trashDao = new TrashDaoImpl(connection);

        inboxService = new InboxServiceImpl(emailDao, emailLinkDao, userDao, trashDao);

        // 테스트 전에 이메일이 삭제 상태가 아닌지 확인하고, 삭제 상태라면 복원
        ensureEmailNotDeleted(TEST_EMAIL_ID, TEST_USER_ID);
    }

    /**
     * 테스트 전에 이메일이 삭제 상태가 아닌지 확인하고, 삭제 상태라면 복원하는 메서드
     */
    private void ensureEmailNotDeleted(int emailId, int userId) throws SQLException {
        // 해당 이메일 링크 조회
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT link_idx, is_deleted FROM EMAIL_LINK " +
                        "WHERE email_idx = ? AND receiver_id = ?")) {
            ps.setInt(1, emailId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int linkId = rs.getInt("link_idx");
                    String isDeleted = rs.getString("is_deleted");

                    // 이미 삭제 상태라면 복원
                    if ("Y".equals(isDeleted)) {
                        System.out.println("이메일이 이미 삭제 상태입니다. 복원합니다.");

                        // 1. 휴지통 항목 삭제
                        try (PreparedStatement deleteTrashPs = connection.prepareStatement(
                                "DELETE FROM TRASH WHERE link_id = ?")) {
                            deleteTrashPs.setInt(1, linkId);
                            deleteTrashPs.executeUpdate();
                        }

                        // 2. 이메일 링크 복원
                        try (PreparedStatement updateLinkPs = connection.prepareStatement(
                                "UPDATE EMAIL_LINK SET is_deleted = 'N' WHERE link_idx = ?")) {
                            updateLinkPs.setInt(1, linkId);
                            updateLinkPs.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    @Test
    void testDeleteReceivedEmail() {
        System.out.println("이메일 삭제 기능 테스트 시작");

        // 삭제 전 이메일 링크 상태 확인
        List<EmailLink> linksBefore = emailLinkDao.getLinksByReceiverId(TEST_USER_ID);
        EmailLink targetLinkBefore = null;

        for (EmailLink link : linksBefore) {
            if (link.getEmailIdx() == TEST_EMAIL_ID) {
                targetLinkBefore = link;
                break;
            }
        }

        assertNotNull(targetLinkBefore, "테스트할 이메일 링크가 존재해야 함");
        assertEquals('N', targetLinkBefore.getIsDeleted(), "이메일이 초기에 삭제 상태가 아니어야 함");

        // 이메일 삭제 실행
        boolean deleteResult = inboxService.deleteReceivedEmail(TEST_EMAIL_ID, TEST_USER_ID);

        // 삭제 결과 확인
        assertTrue(deleteResult, "이메일 삭제가 성공해야 함");

        // 삭제 후 이메일 링크 상태 다시 확인
        List<EmailLink> linksAfter = emailLinkDao.getLinksByReceiverId(TEST_USER_ID);
        boolean found = false;

        for (EmailLink link : linksAfter) {
            if (link.getEmailIdx() == TEST_EMAIL_ID) {
                // 삭제 후에는 getLinksByReceiverId에서 조회되지 않아야 함 (is_deleted='Y'인 것은 제외됨)
                found = true;
                break;
            }
        }

        assertFalse(found, "삭제된 이메일은 받은 메일함에서 조회되지 않아야 함");

        // 링크 상태 직접 확인
        try {
            // 해당 이메일 링크 조회
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT is_deleted FROM EMAIL_LINK " +
                            "WHERE email_idx = ? AND receiver_id = ?")) {
                ps.setInt(1, TEST_EMAIL_ID);
                ps.setInt(2, TEST_USER_ID);

                try (ResultSet rs = ps.executeQuery()) {
                    assertTrue(rs.next(), "이메일 링크가 여전히 존재해야 함");
                    assertEquals("Y", rs.getString("is_deleted"), "이메일 링크가 삭제 상태로 변경되어야 함");
                }
            }

            // 휴지통에 추가되었는지 확인
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT t.trash_idx FROM TRASH t " +
                            "JOIN EMAIL_LINK el ON t.link_id = el.link_idx " +
                            "WHERE el.email_idx = ? AND el.receiver_id = ? AND t.is_restored = 'N'")) {
                ps.setInt(1, TEST_EMAIL_ID);
                ps.setInt(2, TEST_USER_ID);

                try (ResultSet rs = ps.executeQuery()) {
                    assertTrue(rs.next(), "휴지통에 항목이 추가되어야 함");
                    int trashId = rs.getInt("trash_idx");
                    System.out.println("휴지통 항목 ID: " + trashId);
                }
            }

        } catch (SQLException e) {
            fail("데이터베이스 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("이메일 삭제 기능 테스트 성공");
    }

    @Test
    void testDeleteNonExistentEmail() {
        // 존재하지 않는 이메일 ID
        int nonExistentEmailId = 9999;

        // 삭제 시도
        boolean result = inboxService.deleteReceivedEmail(nonExistentEmailId, TEST_USER_ID);

        // 존재하지 않는 이메일 삭제는 실패해야 함
        assertFalse(result, "존재하지 않는 이메일 삭제는 실패해야 함");
    }

}