package com.metaverse.mail.dao.impl.user;

import com.metaverse.mail.common.Session;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.model.User;
import com.metaverse.mail.common.QueryUtil;

import java.sql.*;

/*
 * UserDao 구현 클래스
 * 사용자 정보 CRUD(Create, Read, Update, Delete) 연산을 처리하는 클래스
 */
public class UserDaoImpl implements UserDao {

    private final Connection connection;

    // 생성자 - 데이터베이스 연결을 받는 생성자
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /*
     * 사용자 ID로 사용자 정보 조회
     * 사용자 ID를 이용하여 데이터베이스에서 해당 사용자 정보를 조회
     */
    @Override
    public User findById(int userId) {
        String query = QueryUtil.getQuery("findUserById"); // XML에서 쿼리 가져오기
        User user = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);  // 사용자 ID 매개변수 설정
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = mapToUser(rs);  // 사용자가 존재하면 User 객체로 변환
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 (로깅 추가 가능)
        }

        return user;  // 사용자 반환, 없으면 null 반환
    }

    /*
     * 이메일 주소로 사용자 정보 조회
     * 이메일 주소를 이용하여 해당 사용자 정보 조회 (로그인)
     */
    @Override
    public User findByEmailId(String emailId) {
        String query = QueryUtil.getQuery("findUserByEmailId"); // XML에서 쿼리 가져오기
        User user = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, emailId);  // 이메일 ID 매개변수 설정
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = mapToUser(rs);  // 사용자가 존재하면 User 객체로 변환
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 (로깅 추가 가능)
        }

        return user;  // 사용자 반환, 없으면 null 반환
    }

    /*
     * 새 사용자 추가
     * 새 사용자의 정보를 데이터베이스에 추가하는 메서드 (회원가입)
     */
    @Override
    public boolean insert(User user) {
        String query = QueryUtil.getQuery("insertUser"); // XML에서 쿼리 가져오기

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getEmailId());  // 이메일 ID 설정
            pstmt.setString(2, user.getEmailPwd());  // 비밀번호 설정
            pstmt.setString(3, user.getNickname());  // 닉네임 설정
            pstmt.setString(4, String.valueOf(user.getStatus()));  // 상태 설정
            pstmt.executeUpdate();  // 데이터베이스에 사용자 추가
            return true;  // 성공적으로 추가됨
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 (로깅 추가 가능)
        }

        return false;  // 추가 실패
    }

    @Override
    public boolean updateNickname(int userIdx, String nickname) {
        String query = QueryUtil.getQuery("updateNickname"); // XML에서 쿼리 가져오기

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nickname);  // 새 닉네임 설정
            pstmt.setInt(2, userIdx);  // userIdx는 사용자 식별자(ID)
            int updatedRows = pstmt.executeUpdate(); // 실행된 업데이트된 행의 수
            return updatedRows > 0; // 수정된 행이 있으면 성공

        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 (로깅 추가 가능)
        }

        return false;  // 수정 실패
    }

    @Override
    public boolean updatePassword(int userIdx, String newPassword) {
        String query = QueryUtil.getQuery("updatePassword"); // XML에서 쿼리 가져오기

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newPassword);  // 새 비밀번호 설정
            pstmt.setInt(2, userIdx);  // userIdx는 사용자 식별자(ID)
            int updatedRows = pstmt.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 (로깅 추가 가능)
        }

        return false;  // 수정 실패
    }

    /*
     * 사용자 상태 변경 (예: 탈퇴 처리)
     * 사용자의 상태를 변경하는 메서드 (계정 활성화/탈퇴)
     */
    @Override
    public boolean updateStatus(int userId, char status) {
        String query = QueryUtil.getQuery("updateUserStatus"); // XML에서 쿼리 가져오기

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, String.valueOf(status));  // 상태 설정
            pstmt.setInt(2, userId);  // 사용자 ID 설정
            int updatedRows = pstmt.executeUpdate(); // 실행된 업데이트된 행의 수
            return updatedRows > 0; // 수정된 행이 있으면 성공

        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 (로깅 추가 가능)
        }

        return false;  // 상태 변경 실패
    }

    /*
     * ResultSet을 User 객체로 변환하는 메서드
     * 데이터베이스에서 가져온 ResultSet을 User 객체로 변환하는 유틸리티 메서드
     */
    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setIdx(rs.getInt("idx"));  // 사용자 ID 설정
        user.setEmailId(rs.getString("email_id"));  // 이메일 ID 설정
        user.setEmailPwd(rs.getString("email_pwd"));  // 해시된 비밀번호 설정
        user.setNickname(rs.getString("nickname"));  // 닉네임 설정
        user.setStatus(rs.getString("status").charAt(0));  // 상태 설정
        // null 체크 후 toLocalDateTime 호출
        Timestamp createdAt = rs.getTimestamp("created_at");
        user.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null); // 생성일 설정
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        user.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null); // 수정일 설정
        Timestamp deletedAt = rs.getTimestamp("deleted_at");
        user.setDeletedAt(deletedAt != null ? deletedAt.toLocalDateTime() : null); // 삭제일 설정
        return user;  // User 객체 반환
    }
}
