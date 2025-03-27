package com.metaverse.mail.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 데이터베이스 연결을 관리하는 유틸리티 클래스
 * 
 * 이 클래스는 HikariCP 커넥션 풀을 사용하여 데이터베이스 연결을 효율적으로 관리합니다.
 * 주요 기능:
 *   데이터베이스 연결 풀 초기화 및 관리
 *   애플리케이션에서 필요한 DB 연결 제공
 *   연결 풀 상태 모니터링 및 리소스 정리
 * 
 * 이 클래스는 싱글톤 패턴과 유사하게 정적 멤버와 메서드로 구현되어 있으며,
 * 애플리케이션 전체에서 하나의 연결 풀을 공유합니다.
 * 
 * @author 유진
 * @version 1.0
 */
public class JDBCConnection {
    /** HikariCP 데이터 소스 (커넥션 풀) */
    private static final HikariDataSource dataSource;

    /**
     * 정적 초기화 블록
     * 
     * 클래스가 로드될 때 한 번만 실행되어 데이터베이스 연결 풀을 초기화합니다.
     * 다음 단계로 실행됩니다:
     *   config.properties 파일에서 DB 연결 정보 로드
     *   HikariCP 설정 구성
     *   커넥션 풀 생성
     * 
     * 초기화 중 오류가 발생하면 RuntimeException으로 감싸서 던집니다.
     */
    static {
        try {

            Properties props = new Properties();

            props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));
            
            /* DB 접속을 위한 설정 정보를 구성합니다 */
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // 최대 커넥션 풀 크기 설정 (동시에 사용할 수 있는 최대 DB 연결 수)
            config.setMaximumPoolSize(10);

            // 최소 유휴 커넥션 (사용되지 않는 상태로 풀에 유지할 최소 연결 수)
            config.setMinimumIdle(5);

            // 유휴 상태의 커넥션이 닫히기까지의 시간 (30초)
            // 30초 동안 사용되지 않으면 풀에서 제거됩니다
            config.setIdleTimeout(30000);

            // 커넥션의 최대 수명 (30분)
            // 30분 후에는 사용 중이 아니라면 커넥션을 새로 생성합니다
            config.setMaxLifetime(1800000);

            // 커넥션 획득 타임아웃 (2초)
            // 2초 내에 커넥션을 얻지 못하면 예외 발생
            config.setConnectionTimeout(2000);

            // 설정 정보로 데이터 소스(커넥션 풀) 생성
            dataSource = new HikariDataSource(config);
        } catch (IOException e){
            // 초기화 중 오류 발생 시 런타임 예외로 변환하여 던짐
            throw new RuntimeException("데이터베이스 연결 풀 초기화 중 오류 발생", e);
        }
    }

    /**
     * 데이터베이스 연결 객체를 제공합니다.
     * 
     * 커넥션 풀에서 사용 가능한 연결을 가져옵니다. 이 연결은 사용 후 반드시 close()해야 합니다.
     * 직접 close()를 호출하면 연결이 끊어지지 않고 풀로 반환됩니다.
     * 
     * DB 세션에 대한 참고 사항:
     * DB에는 세션이라는 개념이 존재하며, DB에 연결한 시점에 생성됩니다.
     * 세션을 통해 트랜잭션, 임시 데이터, 캐싱 등의 데이터를 관리합니다.
     * HikariCP는 DB 커넥션 객체를 재사용하지만, JDBC 내부에서 연결을 재사용할 때마다
     * 새로운 세션을 생성하므로 세션 중복 문제는 발생하지 않습니다.
     * 
     * @return 데이터베이스 연결 객체
     * @throws SQLException 연결 획득 중 데이터베이스 오류 발생 시
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 전체 커넥션 풀을 종료합니다.
     * 
     * 애플리케이션 종료 시 호출하여 모든 커넥션을 정리합니다.
     * 이 메서드 호출 후에는 더 이상 getConnection()으로 연결을 획득할 수 없습니다.
     */
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    /**
     * 커넥션 풀의 현재 상태를 콘솔에 출력합니다.
     * 
     * 디버깅과 모니터링을 위한 유틸리티 메서드입니다.
     * 다음 정보를 표시합니다:
     *   총 커넥션 수
     *   활성(사용 중인) 커넥션 수
     *   유휴(사용 가능한) 커넥션 수
     *   대기 중인 커넥션 요청 수
     */
    public static void printConnectionPoolStatus() {
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("[hikariCP 커넥션 풀 상태]");
        System.out.println("총 커넥션 수 : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 : " + poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 수 : " + poolMXBean.getIdleConnections());
        System.out.println("대기 중인 커넥션 요청 수 : " + poolMXBean.getThreadsAwaitingConnection());
    }
}