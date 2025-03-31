package com.metaverse.mail.dao.impl.inbox;

import com.metaverse.mail.common.QueryUtil;
import com.metaverse.mail.dao.interfaces.TrashDao;
import com.metaverse.mail.model.Trash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrashDaoImpl implements TrashDao {
    private final Connection connection;

    /**
     * 생성자를 통해 Connection 객체 주입
     *
     * @param connection 데이터베이스 연결 객체
     */
    public TrashDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * 휴지통에 이메일 추가
     *
     * 데이터베이스에 삭제된 이메일을 휴지통에 추가합니다.
     * 이메일을 삭제(휴지통으로 이동)할 때 호출됩니다.
     *
     * @param linkId 이메일 링크 ID
     * @return 성공 여부
     */
    @Override
    public boolean addToTrash(int linkId) {
        String query = QueryUtil.getQuery("addToTrash");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, linkId);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("휴지통 항목 추가 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Trash> getTrashByUserId(int userId) {
        return new ArrayList<>();
    }

    @Override
    public boolean restoreEmail(int trashId) {
        return false;
    }

    @Override
    public boolean permanentlyDelete(int trashId) {
        return false;
    }

    /**
     * 만료된 이메일 자동 삭제 (30일 이상 경과)
     *
     * 데이터베이스에서 만료 날짜가 지난 휴지통 항목을 자동으로 삭제합니다.
     * 시스템에서 정기적으로 호출하여 오래된 이메일을 정리합니다.
     *
     * @return 삭제된 이메일 수
     */
    @Override
    public int deleteExpiredEmails() {
        return 0;
    }

}