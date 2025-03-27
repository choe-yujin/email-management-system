package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.inbox.SentEmailDto;

import java.util.List;

/**
 * 보낸 메일함 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 * 
 * 이 인터페이스는 보낸 이메일의 조회 및 관리와 관련된 비즈니스 로직을 정의합니다.
 * 보낸 이메일 목록 조회, 상세 정보 조회, 이메일 삭제 등의 기능을 포함합니다.
 * 
 * 서비스 계층은 프레젠테이션 계층(View)과 데이터 액세스 계층(DAO) 사이의
 * 중간 역할을 하며, 비즈니스 규칙 및 트랜잭션 처리를 담당합니다.
 * 
 * 담당 개발자: 효민(개발자 C)
 * 
 * @author 유진
 * @version 1.0
 */
public interface InboxService {
    /**
     * 보낸 이메일 목록 조회
     * 
     * 특정 사용자가 보낸 모든 이메일의 목록을 조회합니다.
     * 최신 이메일부터 표시되며, 각 이메일의 요약 정보가 포함됩니다.
     * 
     * 주요 처리 내용:
     *   사용자 ID 유효성 검증
     *   보낸 이메일 목록 조회
     *   각 이메일의 수신자 정보 조회
     *   DTO 변환 및 정렬
     * 
     * @param userId 사용자 ID
     * @return 보낸 이메일 목록
     */
    List<SentEmailDto> getSentEmails(int userId);

    /**
     * 보낸 이메일 상세 정보 조회
     * 
     * 특정 보낸 이메일의 상세 내용을 조회합니다.
     * 보낸 메일함에서 이메일을 클릭할 때 호출됩니다.
     * 
     * 주요 처리 내용:
     *   이메일 존재 여부 확인
     *   사용자 권한 확인(발신자인지)
     *   모든 수신자 정보 조회
     *   이메일 상세 정보 DTO 변환
     * 
     * @param emailId 이메일 ID
     * @return 이메일 상세 정보
     */
    SentEmailDto getSentEmailDetails(int emailId);

    /**
     * 받은 이메일 삭제 (휴지통으로 이동)
     * 
     * 받은 이메일을 삭제 상태로 변경하고 휴지통으로 이동시킵니다.
     * 받은 메일함에서 이메일 삭제 시 호출됩니다.
     * 
     * 주요 처리 내용:
     *   이메일 존재 여부 확인
     *   사용자 권한 확인(수신자인지)
     *   이메일 링크 상태 변경
     *   휴지통 항목 생성
     * 
     * @param emailId 이메일 ID
     * @param userId 사용자 ID
     * @return 삭제 성공 여부(true: 성공, false: 실패)
     */
    boolean deleteReceivedEmail(int emailId, int userId);

    /**
     * 보낸 이메일 삭제 (휴지통으로 이동)
     * 
     * 보낸 이메일을 삭제 상태로 변경하고 휴지통으로 이동시킵니다.
     * 보낸 메일함에서 이메일 삭제 시 호출됩니다.
     * 
     * 주요 처리 내용:
     *   이메일 존재 여부 확인
     *   사용자 권한 확인(발신자인지)
     *   이메일 링크 상태 변경
     *   휴지통 항목 생성
     * 
     * @param emailId 이메일 ID
     * @return 삭제 성공 여부(true: 성공, false: 실패)
     */
    boolean deleteSentEmail(int emailId);
}