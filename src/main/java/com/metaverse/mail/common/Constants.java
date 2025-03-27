package com.metaverse.mail.common;

/**
 * 애플리케이션 전체에서 사용하는 상수를 정의하는 클래스
 */
public class Constants {
    /** 우리 이메일의 도메인 */
    public static final String EMAIL_DOMAIN = "@metaverse.com";

    /**
     * 사용자 아이디를 완전한 이메일 주소로 변환
     *
     * @param userId 사용자 아이디
     * @return 완전한 이메일 주소 (userId@metaverse.com)
     */
    public static String toEmail(String userId) {
        return userId + EMAIL_DOMAIN;
    }

    /**
     * 완전한 이메일 주소에서 사용자 아이디 부분만 추출
     *
     * @param email 이메일 주소
     * @return 사용자 아이디 부분
     */
    public static String fromEmail(String email) {
        if (email == null || !email.endsWith(EMAIL_DOMAIN)) {
            return email;
        }
        return email.substring(0, email.indexOf(EMAIL_DOMAIN));
    }
}