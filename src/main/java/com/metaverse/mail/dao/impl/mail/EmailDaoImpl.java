package com.metaverse.mail.dao.impl.mail;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.common.QueryUtil;
import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.model.Email;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 이메일 데이터 액세스 객체 구현 클래스
 *
 * EmailDao 인터페이스를 구현하여 이메일 관련 데이터베이스 작업을 수행합니다.
 *
 * @author 유진
 * @version 1.0
 */
public class EmailDaoImpl implements EmailDao {

    private final Connection connection;

    /**
     * 기본 생성자 - 기본 데이터베이스 연결 사용
     */
    public EmailDaoImpl() {
        try {
            this.connection = JDBCConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패", e);
        }
    }

    /**
     * 생성자를 통해 Connection 객체 주입 (테스트용)
     *
     * @param connection 데이터베이스 연결 객체
     */
    public EmailDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * 새 이메일 생성
     *
     * 데이터베이스에 새로운 이메일을 저장합니다.
     * 이메일 작성 및 전송 시 호출됩니다.
     *
     * @param email 이메일 객체
     * @return 생성된 이메일 ID, 실패 시 -1
     */
    @Override
    public int createEmail(Email email) {
        String query = QueryUtil.getQuery("createEmail");
        int emailId = -1;

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // 쿼리 파라미터 설정
            ps.setInt(1, email.getSenderId());
            ps.setString(2, email.getTitle());
            ps.setString(3, email.getBody());

            // 쿼리 실행
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                // 생성된 ID 가져오기
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        emailId = generatedKeys.getInt(1);
                    }
                }
            }

            // 생성 실패 시 별도 쿼리로 마지막 삽입 ID 조회 시도
            if (emailId == -1) {
                String lastIdQuery = QueryUtil.getQuery("getLastInsertId");
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(lastIdQuery)) {
                    if (rs.next()) {
                        emailId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailId;
    }


    /**
     * 이메일 ID로 이메일 조회
     *
     * 데이터베이스에서 지정된 ID에 해당하는 이메일 정보를 검색합니다.
     * 이메일 상세 보기 시 호출됩니다.
     *
     * @param emailId 이메일 ID
     * @return 이메일 객체, 없으면 null
     */
    @Override
    public Email getEmailById(int emailId) {
        String query = QueryUtil.getQuery("getEmailById");
        Email email = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, emailId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    email = new Email();
                    email.setEmailIdx(rs.getInt("email_idx"));
                    email.setSenderId(rs.getInt("sender_id"));
                    email.setTitle(rs.getString("title"));
                    email.setBody(rs.getString("body"));
                    email.setStatus(rs.getString("status").charAt(0));

                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        email.setCreatedAt(createdAt.toLocalDateTime());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }

    /**
     * 발신자 ID로 보낸 이메일 목록 조회
     *
     * 특정 사용자가 보낸 이메일 목록을 데이터베이스에서 조회합니다.
     * 보낸 메일함 기능 구현 시 호출됩니다.
     *
     * @param senderId 발신자 ID
     * @return 이메일 목록
     */
    @Override
    public List<Email> getEmailsBySenderId(int senderId) {
        String query = QueryUtil.getQuery("getEmailsBySenderId");
        List<Email> emails = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, senderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Email email = new Email();
                    email.setEmailIdx(rs.getInt("email_idx"));
                    email.setSenderId(rs.getInt("sender_id"));
                    email.setTitle(rs.getString("title"));
                    email.setBody(rs.getString("body"));
                    email.setStatus(rs.getString("status").charAt(0));

                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        email.setCreatedAt(createdAt.toLocalDateTime());
                    }

                    emails.add(email);
                }
            }
        } catch (SQLException e) {
            System.err.println("보낸 이메일 목록 조회 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return emails;
    }

    /**
     * 키워드로 이메일 검색
     *
     * 제목이나 내용에 특정 키워드가 포함된 이메일을 검색합니다.
     * 이메일 검색 기능 구현 시 호출됩니다.
     *
     * @param keyword 검색 키워드
     * @param userId  사용자 ID (발신/수신 이메일 모두 검색)
     * @return 검색된 이메일 목록
     */
    @Override
    public List<Email> searchEmails(String keyword, int userId) {
        return List.of();
    }

    /**
     * 이메일 삭제 (실제 삭제가 아닌 상태 변경)
     *
     * 데이터베이스에서 이메일의 상태를 변경하여 삭제 처리합니다.
     * 실제로 데이터베이스에서 레코드를 삭제하지 않고 상태만 변경합니다.
     *
     * @param emailId 이메일 ID
     * @return 성공 여부
     */
    @Override
    public boolean deleteEmail(int emailId) {
        return false;
    }
}
