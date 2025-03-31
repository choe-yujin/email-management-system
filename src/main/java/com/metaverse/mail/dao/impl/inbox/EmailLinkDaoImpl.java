package com.metaverse.mail.dao.impl.inbox;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.common.QueryUtil;
import com.metaverse.mail.dao.interfaces.EmailLinkDao;
import com.metaverse.mail.model.EmailLink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailLinkDaoImpl implements EmailLinkDao {
    private final Connection connection;

    /**
     * 기본 생성자 - 기본 데이터베이스 연결 사용
     */
    public EmailLinkDaoImpl() {
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
    public EmailLinkDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * 이메일 링크 생성 (이메일-수신자 연결)
     *
     * 데이터베이스에 이메일과 수신자 간의 연결 정보를 저장합니다.
     * 이메일 발송 시 각 수신자마다 호출됩니다.
     *
     * @param emailId    이메일 ID
     * @param receiverId 수신자 ID
     * @return 성공 여부
     */
    @Override
    public boolean createEmailLink(int emailId, int receiverId) {
        String query = QueryUtil.getQuery("createEmailLink");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, emailId);
            ps.setInt(2, receiverId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("이메일 링크 생성 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 링크 ID로 이메일 링크 조회
     *
     * 데이터베이스에서 지정된 링크 ID에 해당하는 이메일 링크 정보를 검색합니다.
     *
     * @param linkId 링크 ID
     * @return 이메일 링크 객체, 없으면 null
     */
    @Override
    public EmailLink getLinkById(int linkId) {
        return null;
    }

    /**
     * 수신자 ID로 받은 이메일 링크 목록 조회
     *
     * 특정 사용자가 받은 모든 이메일 링크 목록을 데이터베이스에서 조회합니다.
     * 받은 메일함 기능 구현 시 호출됩니다.
     *
     * @param receiverId 수신자 ID
     * @return 이메일 링크 목록
     */
    @Override
    public List<EmailLink> getLinksByReceiverId(int receiverId) {
        List<EmailLink> emailLinks = new ArrayList<>();
        String query = QueryUtil.getQuery("getLinksByReceiverId");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, receiverId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmailLink emailLink = new EmailLink();
                    emailLink.setLinkIdx(rs.getInt("link_idx"));
                    emailLink.setReceiverId(rs.getInt("receiver_id"));
                    emailLink.setEmailIdx(rs.getInt("email_idx"));

                    String isReaded = rs.getString("is_readed");
                    emailLink.setIsReaded(isReaded != null && !isReaded.isEmpty() ? isReaded.charAt(0) : 'N');

                    String isDeleted = rs.getString("is_deleted");
                    emailLink.setIsDeleted(isDeleted != null && !isDeleted.isEmpty() ? isDeleted.charAt(0) : 'N');

                    emailLinks.add(emailLink);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailLinks;
    }

    /**
     * 이메일을 읽음 상태로 변경
     *
     * 데이터베이스에서 이메일 링크의 읽음 상태를 변경합니다.
     * 이메일 상세 내용 조회 시 호출됩니다.
     *
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    @Override
    public boolean markAsRead(int linkId) {
        return false;
    }

    /**
     * 이메일을 삭제 상태로 변경
     *
     * 데이터베이스에서 이메일 링크의 삭제 상태를 변경합니다.
     * 이메일 삭제(휴지통으로 이동) 시 호출됩니다.
     *
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    @Override
    public boolean markAsDeleted(int linkId) {
        return false;
    }

    /**
     * 이메일을 복구 (삭제 취소)
     *
     * 데이터베이스에서 이메일 링크의 삭제 상태를 정상 상태로 변경합니다.
     * 휴지통에서 이메일 복구 시 호출됩니다.
     *
     * @param linkId 링크 ID
     * @return 성공 여부
     */
    @Override
    public boolean restoreEmail(int linkId) {
        return false;
    }
}
