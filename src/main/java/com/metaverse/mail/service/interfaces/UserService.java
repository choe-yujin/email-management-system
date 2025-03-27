package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.user.UserLoginDto;
import com.metaverse.mail.dto.user.UserProfileDto;
import com.metaverse.mail.dto.user.UserRegisterDto;
import com.metaverse.mail.model.User;

/**
 * 사용자 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 * 
 * 이 인터페이스는 사용자 계정 관리와 관련된 비즈니스 로직을 정의합니다.
 * 사용자 인증, 계정 생성, 정보 수정, 비밀번호 변경, 계정 비활성화 등의
 * 기능을 포함합니다.
 * 
 * 서비스 계층은 프레젠테이션 계층(View)과 데이터 액세스 계층(DAO) 사이의
 * 중간 역할을 하며, 비즈니스 규칙 및 트랜잭션 처리를 담당합니다.
 * 
 * 담당 개발자: 우선(개발자 A)
 * 
 * @author 유진
 * @version 1.0
 */
public interface UserService {
    /**
     * 회원가입 처리
     * 
     * 새로운 사용자 계정을 생성합니다. 입력받은 정보를 검증한 후
     * 유효한 경우 데이터베이스에 사용자 정보를 저장합니다.
     * 
     * 주요 처리 내용:
     *   이메일 아이디 형식 및 중복 검사
     *   비밀번호 유효성 검사 및 암호화
     *   필수 정보 입력 확인
     *   사용자 정보 저장
     * 
     * @param userDto 사용자 등록 정보(이메일, 비밀번호, 닉네임 등)
     * @return 회원가입 성공 여부(true: 성공, false: 실패)
     */
    boolean register(UserRegisterDto userDto);

    /**
     * 로그인 처리
     * 
     * 사용자 인증을 수행합니다. 입력받은 이메일과 비밀번호를 검증하고
     * 유효한 경우 사용자 세션을 생성합니다.
     * 
     * 주요 처리 내용:
     *   이메일 존재 여부 확인
     *   비밀번호 일치 여부 확인
     *   계정 상태 확인(활성/비활성)
     *   로그인 성공 시 세션 정보 설정
     * 
     * @param loginDto 로그인 정보(이메일, 비밀번호)
     * @return 인증된 사용자 정보, 인증 실패 시 null
     */
    User login(UserLoginDto loginDto);

    /**
     * 사용자 프로필 정보 수정
     * 
     * 사용자의 프로필 정보를 업데이트합니다. 닉네임 등의
     * 기본 정보를 변경할 수 있습니다.
     * 
     * 주요 처리 내용:
     *   현재 비밀번호 확인
     *   입력 정보 유효성 검사
     *   사용자 정보 업데이트
     * 
     * @param profileDto 수정할 프로필 정보(닉네임, 현재 비밀번호, 새 비밀번호 등)
     * @param userId 사용자 ID
     * @return 프로필 수정 성공 여부(true: 성공, false: 실패)
     */
    boolean updateProfile(UserProfileDto profileDto, int userId);

    /**
     * 비밀번호 변경
     * 
     * 사용자의 비밀번호를 변경합니다. 현재 비밀번호 확인 후
     * 새 비밀번호로 업데이트합니다.
     * 
     * 주요 처리 내용:
     *   현재 비밀번호 일치 여부 확인
     *   새 비밀번호 유효성 검사
     *   비밀번호 암호화 및 업데이트
     * 
     * @param oldPassword 현재 비밀번호
     * @param newPassword 새 비밀번호
     * @param userId 사용자 ID
     * @return 비밀번호 변경 성공 여부(true: 성공, false: 실패)
     */
    boolean changePassword(String oldPassword, String newPassword, int userId);

    /**
     * 회원 탈퇴 처리
     * 
     * 사용자 계정을 비활성화 상태로 변경합니다. 실제로 데이터는 삭제되지 않고
     * 상태만 변경되어 나중에 복구 가능합니다.
     * 
     * 주요 처리 내용:
     *   사용자 존재 여부 확인
     *   사용자 상태를 비활성화로 변경
     *   사용자 세션 종료
     * 
     * @param userId 비활성화할 사용자 ID
     * @return 계정 비활성화 성공 여부(true: 성공, false: 실패)
     */
    boolean deactivateAccount(int userId);
}