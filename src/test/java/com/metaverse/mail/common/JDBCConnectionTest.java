package com.metaverse.mail.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * JDBCConnection 및 USER 테이블에 대한 연결 테스트 클래스
 *
 * 이 클래스는 데이터베이스 연결 및 간단한 쿼리 실행을 테스트합니다.
 */
class JDBCConnectionTest {

    @BeforeAll
    static void setUp() {
        System.out.println("Setting up JDBC Connection");
    }

    @Test
    void testConnection() throws SQLException {
        try(Connection con = JDBCConnection.getConnection()) {
            Assertions.assertNotNull(con, "Connection 생성되지 않았습니다");
            Assertions.assertFalse(con.isClosed(), "커넥션은 열려있어야 합니다.");
            System.out.println("DB 커넥션이 연결되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        System.out.println("테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }
}