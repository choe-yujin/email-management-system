package com.metaverse.mail.dto.user;

/**
 * 사용자 회원가입 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 회원가입 화면에서 사용자가 입력한 이메일 아이디, 비밀번호, 닉네임 정보를
 * 서비스 계층으로 전달하는 데 사용됩니다.
 * 
 * DTO 패턴을 사용하여 프레젠테이션 계층과 비즈니스 계층 사이의
 * 데이터 교환을 명확하게 정의합니다.
 * 
 * 담당 개발자: 우선(개발자 A)
 * 
 * @author 유진
 * @version 1.0
 */
public class UserRegisterDto {
    /** 사용자 이메일 아이디 (로그인 ID) */
    private String emailId;
    
    /** 사용자 비밀번호 */
    private String password;
    
    /** 사용자 닉네임 */
    private String nickname;

    /**
     * 기본 생성자
     */
    public UserRegisterDto() {}

    /**
     * 모든 필드를 초기화하는 생성자
     * 
     * @param emailId 사용자 이메일 아이디
     * @param password 사용자 비밀번호
     * @param nickname 사용자 닉네임
     */
    public UserRegisterDto(String emailId, String password, String nickname) {
        this.emailId = emailId;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * 이메일 아이디 반환
     * 
     * @return 사용자 이메일 아이디
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

    /**
     * 닉네임 반환
     * 
     * @return 사용자 닉네임
     */
    public String getNickname() {
        return nickname;
    }

}