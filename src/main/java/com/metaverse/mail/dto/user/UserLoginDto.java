package com.metaverse.mail.dto.user;

/**
 * 사용자 로그인 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 로그인 화면에서 사용자가 입력한 이메일 주소와 비밀번호를
 * 서비스 계층으로 전달하는 데 사용됩니다.
 * 
 * DTO 패턴을 사용하여 프레젠테이션 계층과 비즈니스 계층 사이의
 * 데이터 교환을 명확하게 정의합니다.
 * 
 * @author 유진
 * @version 1.0
 */
public class UserLoginDto {
    /** 사용자 이메일 주소 (로그인 ID) */
    private String emailId;
    
    /** 사용자 비밀번호 */
    private String password;

    /**
     * 기본 생성자
     */
    public UserLoginDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param emailId 사용자 이메일 주소
     * @param password 사용자 비밀번호
     */
    public UserLoginDto(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    /**
     * 이메일 주소 반환
     * 
     * @return 사용자 이메일 주소
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * 비밀번호 반환
     * 
     * @return 사용자 비밀번호
     */
    public String getPassword() {
        return password;
    }

}