package com.metaverse.mail.service.interfaces;

import com.metaverse.mail.dto.inbox.TrashEmailDto;

import java.util.List;

/**
 * 휴지통 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 *
 * 이 인터페이스는 휴지통에 있는 이메일의 조회 및 관리와 관련된 비즈니스 로직을 정의합니다.
 * 휴지통 이메일 목록 조회, 상세 정보 조회, 복구, 영구 삭제 등의 기능을 포함합니다.
 *
 * 서비스 계층은 프레젠테이션 계층(View)과 데이터 액세스 계층(DAO) 사이의
 * 중간 역할을 하며, 비즈니스 규칙 및 트랜잭션 처리를 담당합니다.
 *
 * 담당 개발자: 효민(개발자 C)
 *
 * @author 유진
 * @version 1.0
 */
public interface TrashService {
    /**
     * 휴지통 이메일 목록 조회
     *
     * 특정 사용자의 휴지통에 있는 모든 이메일 목록을 조회합니다.
     * 최근에 삭제된 이메일부터 표시되며, 각 이메일의 만료 예정일이 포함됩니다.
     *
     * 주요 처리 내용:
     *   사용자 ID 유효성 검증
     *   휴지통 항목 목록 조회
     *   각 항목의 이메일 정보 조회
     *   DTO 변환 및 정렬
     *
     * @param userId 사용자 ID
     * @return 휴지통 이메일 목록
     */
    List<TrashEmailDto> getTrashEmails(int userId);

    /**
     * 휴지통 이메일 상세 정보 조회
     *
     * 휴지통에 있는 특정 이메일의 상세 내용을 조회합니다.
     * 휴지통에서 이메일을 클릭할 때 호출됩니다.
     *
     * 주요 처리 내용:
     *   휴지통 항목 존재 여부 확인
     *   사용자 권한 확인
     *   이메일 정보 조회
     *   DTO 변환
     *
     * @param trashId 휴지통 ID
     * @return 이메일 상세 정보
     */
    TrashEmailDto getTrashEmailDetails(int trashId);

    /**
     * 이메일 복구 (휴지통에서 원래 메일함으로)
     *
     * 휴지통에 있는 이메일을 원래 메일함으로 복구합니다.
     * 휴지통에서 복구 기능 선택 시 호출됩니다.
     *
     * 주요 처리 내용:
     *   휴지통 항목 존재 여부 확인
     *   사용자 권한 확인
     *   이메일 링크 상태 변경
     *   휴지통 항목 복구 상태로 변경
     *
     * @param trashId 휴지통 ID
     * @return 성공 여부(true: 성공, false: 실패)
     */
    boolean restoreEmail(int trashId);

    /**
     * 이메일 영구 삭제
     *
     * 휴지통에 있는 특정 이메일을 영구적으로 삭제합니다.
     * 휴지통에서 영구 삭제 기능 선택 시 호출됩니다.
     *
     * 주요 처리 내용:
     *   휴지통 항목 존재 여부 확인
     *   사용자 권한 확인
     *   이메일 데이터 영구 삭제
     *   휴지통 항목 제거
     *
     * @param trashId 휴지통 ID
     * @return 성공 여부(true: 성공, false: 실패)
     */
    boolean permanentlyDeleteEmail(int trashId);

    /**
     * 만료된 이메일 자동 삭제
     *
     * 휴지통에서 일정 기간(보통 30일)이 지난 이메일을 자동으로 영구 삭제합니다.
     * 시스템 스케줄러에 의해 주기적으로 호출됩니다.
     *
     * 주요 처리 내용:
     *   만료 기간이 지난 휴지통 항목 조회
     *   해당 이메일 데이터 영구 삭제
     *   휴지통 항목 제거
     *   삭제된 항목 수 집계
     *
     * @return 삭제된 이메일 수
     */
    int cleanupExpiredEmails();
}