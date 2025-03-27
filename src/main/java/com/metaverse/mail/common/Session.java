package com.metaverse.mail.common;

import com.metaverse.mail.model.User;

/**
 * 사용자 세션 관리 클래스
 * 
 * 이 클래스는 애플리케이션 내에서 현재 로그인한 사용자 정보를 관리합니다.
 * 싱글톤 패턴을 사용하여 애플리케이션 전체에서 하나의 세션 인스턴스만 유지됩니다.
 * 
 * 주요 기능:
 *   사용자 로그인 상태 관리
 *   현재 로그인한 사용자 정보 제공
 *   로그아웃 처리
 * 
 * @author 유진
 * @version 1.0
 */
public class Session {
    /** 싱글톤 인스턴스 */
    private static Session instance;
    
    /** 현재 로그인한 사용자 정보 */
    private User currentUser;
    
    /** 로그인 상태 플래그 */
    private boolean loggedIn = false;

    /**
     * 기본 생성자 - private으로 선언하여 외부에서 직접 인스턴스 생성을 방지합니다.
     * 싱글톤 패턴을 구현하기 위한 구성입니다.
     */
    private Session() {} // 싱글톤 패턴

    /**
     * 싱글톤 인스턴스를 반환합니다.
     * 
     * 인스턴스가 없는 경우 새로 생성하여 반환합니다(지연 초기화).
     * 이 메서드를 통해서만 Session 객체에 접근할 수 있습니다.
     * 
     * @return Session 인스턴스
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * 사용자 로그인 처리를 수행합니다.
     * 
     * 사용자 객체가 유효한 경우, 해당 사용자 정보를 세션에 저장하고
     * 로그인 상태를 true로 변경합니다.
     * 
     * @param user 로그인할 사용자 객체
     * @return 로그인 성공 여부
     */
    public boolean login(User user) {
        if (user != null) {
            this.currentUser = user;
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    /**
     * 로그아웃 처리를 수행합니다.
     * 
     * 현재 사용자 정보를 초기화하고 로그인 상태를 false로 변경합니다.
     */
    public void logout() {
        this.currentUser = null;
        this.loggedIn = false;
    }

    /**
     * 현재 로그인한 사용자 정보를 반환합니다.
     * 
     * @return 현재 로그인한 사용자 객체, 로그인되지 않은 경우 null
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * 현재 로그인 상태를 반환합니다.
     * 
     * @return 로그인 상태 (true: 로그인됨, false: 로그인되지 않음)
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * 현재 로그인한 사용자의 ID를 반환합니다.
     * 
     * @return 사용자 ID, 로그인되지 않은 경우 -1
     */
    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getIdx() : -1;
    }

    /**
     * 현재 로그인한 사용자의 이메일 주소를 반환합니다.
     * 
     * @return 사용자 이메일 주소, 로그인되지 않은 경우 null
     */
    public String getCurrentUserEmail() {
        return currentUser != null ? currentUser.getEmailId() : null;
    }

    /**
     * 현재 로그인한 사용자의 닉네임을 반환합니다.
     * 
     * @return 사용자 닉네임, 로그인되지 않은 경우 null
     */
    public String getCurrentUserNickname() {
        return currentUser != null ? currentUser.getNickname() : null;
    }
}