package com.metaverse.mail.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metaverse.mail.model.User;

/**
 * JDBCConnection 및 USER 테이블에 대한 연결 테스트 클래스
 *
 * 이 클래스는 데이터베이스 연결 및 간단한 쿼리 실행을 테스트합니다.
 */
public class JDBCConnectionTest {

    public static void main(String[] args) {
        // 연결 테스트
        testConnection();

        // 사용자 조회 테스트 (이메일로 검색)
        testFindUserByEmail("kim@example.com");

        // 연결 종료
        JDBCConnection.close();

        System.out.println("모든 테스트가 완료되었습니다.");
    }

    /**
     * 데이터베이스 연결 테스트
     */
    private static void testConnection() {
        System.out.println("=== 데이터베이스 연결 테스트 ===");
        try {
            Connection conn = JDBCConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("데이터베이스 연결 성공!");
            } else {
                System.out.println("데이터베이스 연결 실패!");
            }
        } catch (Exception e) {
            System.out.println("데이터베이스 연결 테스트 중 오류 발생");
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * 이메일로 사용자 조회 테스트
     *
     * @param email 조회할 사용자 이메일
     */
    private static void testFindUserByEmail(String email) {
        System.out.println("=== 이메일로 사용자 조회 테스트 ===");
        System.out.println("검색할 이메일: " + email);

        String sql = "SELECT * FROM USER WHERE email_id = ?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdx(rs.getInt("idx"));
                    user.setEmailId(rs.getString("email_id"));
                    user.setEmailPwd(rs.getString("email_pwd"));
                    user.setNickname(rs.getString("nickname"));
                    user.setStatus(rs.getString("status").charAt(0));

                    System.out.println("사용자 조회 성공!");
                    System.out.println("ID: " + user.getIdx());
                    System.out.println("이메일: " + user.getEmailId());
                    System.out.println("닉네임: " + user.getNickname());
                    System.out.println("상태: " + user.getStatus());
                } else {
                    System.out.println("해당 이메일의 사용자가 존재하지 않습니다.");
                }
            }
        } catch (SQLException e) {
            System.out.println("사용자 조회 테스트 중 오류 발생");
            e.printStackTrace();
        }
        System.out.println();
    }
}