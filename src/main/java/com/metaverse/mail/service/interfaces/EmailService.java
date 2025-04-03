package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.mail.*;

import java.util.List;
import java.util.Map;

/**
 * 이메일 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 * 
 * 이 인터페이스는 이메일 송수신 및 검색과 관련된 비즈니스 로직을 정의합니다.
 * 이메일 작성, 조회, 검색, 답장 등의 핵심 기능을 포함합니다.
 * 
 * 서비스 계층은 프레젠테이션 계층(View)과 데이터 액세스 계층(DAO) 사이의
 * 중간 역할을 하며, 비즈니스 규칙 및 트랜잭션 처리를 담당합니다.
 * 
 * 담당 개발자: 유진(개발자 B)
 * 
 * @author 유진
 * @version 1.0
 */
public interface EmailService {
    /**
     * 이메일 발송
     *
     * @param emailDto 이메일 작성 정보(수신자, 제목, 내용 등)
     * @param senderId 발신자 ID
     * @return 결과 정보를 담은 Map(success: 성공 여부, message: 결과 메시지)
     */
    public Map<String, Object> sendEmail(EmailComposeDto emailDto, int senderId);

    /**
     * 받은 이메일 목록 조회
     * 
     * 특정 사용자가 받은 모든 이메일의 목록을 조회합니다.
     * 삭제되지 않은 이메일만 포함되며, 최신 이메일부터 표시됩니다.
     * 
     * 주요 처리 내용:
     *   사용자 ID 유효성 검증
     *   받은 이메일 목록 조회
     *   발신자 정보 추가
     *   DTO 변환 및 정렬
     * 
     * @param userId 사용자 ID
     * @return 받은 이메일 목록
     */
    List<ReceivedEmailDto> getReceivedEmails(int userId);

    /**
     * 이메일 상세 정보 조회
     * 
     * 특정 이메일의 상세 내용을 조회합니다.
     * 이메일을 읽을 때 호출되며, 읽음 상태로 자동 변경됩니다.
     * 
     * 주요 처리 내용:
     *   이메일 존재 여부 확인
     *   사용자 권한 확인(이메일 수신자 또는 발신자인지)
     *   읽음 상태로 변경
     *   상세 정보 DTO 변환
     * 
     * @param emailId 이메일 ID
     * @param userId 사용자 ID
     * @return 이메일 상세 정보
     */
    ReceivedEmailDto getEmailDetails(int emailId, int userId);


    // 검색 관련 메서드 분리/받은 메일
    List<ReceivedEmailSearchDto> searchReceivedEmails(String keyword, int userId);

    // 검색 관련 메서드 분리/보낸 메일
    List<SentEmailSearchDto> searchSentEmails(String keyword, int userId);


    /**
     * 이메일에 답장
     * 
     * 받은 이메일에 대한 답장을 보냅니다.
     * 원본 이메일 정보를 참조하여 답장을 구성합니다.
     * 
     * 주요 처리 내용:
     *   원본 이메일 존재 여부 확인
     *   원본 발신자를 수신자로 설정
     *   제목에 'Re:' 접두어 추가
     *   원본 이메일 인용 추가
     *   새 이메일 저장 및 발송
     * 
     * @param originalEmailId 원본 이메일 ID
     * @param replyContent 답장 내용
     * @param senderId 발신자 ID
     * @return 답장 발송 성공 여부(true: 성공, false: 실패)
     */
    boolean replyToEmail(int originalEmailId, String replyContent, int senderId);
}