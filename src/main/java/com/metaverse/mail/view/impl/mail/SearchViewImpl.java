package com.metaverse.mail.view.impl.mail;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dto.mail.EmailSearchDto;
import com.metaverse.mail.dto.mail.ReceivedEmailSearchDto;
import com.metaverse.mail.dto.mail.SentEmailSearchDto;
import com.metaverse.mail.service.interfaces.EmailService;
import com.metaverse.mail.view.interfaces.mail.InboxView;
import com.metaverse.mail.view.interfaces.mail.SearchView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * 메일 검색 화면 구현 클래스
 *
 * @author 유진
 * @version 1.0
 */
public class SearchViewImpl implements SearchView {

    /** 사용자 입력을 처리하는 Scanner 객체 */
    private final Scanner scanner;

    /** 콘솔 UI를 관리하는 헬퍼 */
    private final ConsoleHelper consoleHelper;

    /** 이메일 서비스 */
    private final EmailService emailService;

    /** 받은 메일함 화면 (상세 보기 재활용) */
    private final InboxView inboxView;

    /** 날짜 포맷팅을 위한 DateTimeFormatter */
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 생성자
     *
     * @param scanner 사용자 입력을 읽기 위한 Scanner 객체
     * @param emailService 이메일 서비스
     * @param inboxView 받은 메일함 화면 (상세 보기 재활용)
     */
    public SearchViewImpl(Scanner scanner, EmailService emailService, InboxView inboxView) {
        this.scanner = scanner;
        this.consoleHelper = new ConsoleHelper(scanner);
        this.emailService = emailService;
        this.inboxView = inboxView;
    }

    /**
     * 메일 검색 폼 표시
     */
    @Override
    public void showSearchForm() {
        // 헤더 표시
        consoleHelper.displayHeader("🔍 받은메일함 검색");

        // 검색어 입력 받기
        String keyword = consoleHelper.getStringInput("검색할 키워드 입력(Enter: 뒤로가기): ");

        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        // 수신메일만 검색 실행
        List<ReceivedEmailSearchDto> results = emailService.searchReceivedEmails(keyword, Session.getInstance().getCurrentUserId());

        // 검색 결과 표시
        if (results.isEmpty()) {
            showNoSearchResults(keyword);
        } else {
            showReceivedEmailSearchResults(keyword, results);
        }
    }

    @Override
    public void showReceivedEmailSearchResults(String keyword, List<ReceivedEmailSearchDto> results) {
        consoleHelper.displayHeader("🔍 받은메일함 검색");
        System.out.println("검색할 키워드 입력: \"" + keyword + "\"");
        consoleHelper.displayDivider();

        System.out.println("검색 결과:");
        int index = 1;
        for (ReceivedEmailSearchDto email : results) {
            String dateStr = email.getSentDate().format(dateFormatter);
            String readStatus = email.isRead() ? "[읽음]" : "[미확인]";

            // 발신자 이름이 비어있는 경우 "알 수 없음"으로 표시
            String senderName = email.getSenderName();
            if (senderName == null || senderName.trim().isEmpty()) {
                senderName = "알 수 없음";
            }

            System.out.println(index + ". " + readStatus + " " + senderName + " - \"" +
                    email.getTitle() + "\" (" + dateStr + ")");
            index++;
        }

        consoleHelper.displayDivider();

        // 이메일 선택 또는 뒤로 가기
        int choice = consoleHelper.getIntInput("조회할 메일 번호 입력 (0: 뒤로 가기): ", 0, results.size());

        if (choice > 0) {
            // 선택한 이메일 상세 정보 표시
            ReceivedEmailSearchDto selected = results.get(choice - 1);
            inboxView.showEmailDetails(selected.getEmailId());
        }
    }

    @Override
    public void showSentEmailSearchResults(String keyword, List<SentEmailSearchDto> results) {
    }

    /**
     * 특정 이메일 상세 정보 표시
     *
     * @param emailId 이메일 ID
     * @param emailType 이메일 유형 ("수신" 또는 "발신")
     */
    @Override
    public void showEmailDetails(int emailId, String emailType) {
        // 이메일 유형에 따라 다른 처리
        if (emailType.contains("수신")) {
            // 수신 이메일인 경우 InboxView를 사용하여 상세 정보 표시
            int userId = Session.getInstance().getCurrentUserId();
            inboxView.showEmailDetails(emailId);
        } else {
            // 발신 이메일인 경우 아직 기능이 구현되지 않았으므로 안내 메시지 표시
            System.out.println("→ 보낸 메일 상세 보기는 아직 구현되지 않았습니다.");
            System.out.println("→ 향후 업데이트 예정입니다.");
            consoleHelper.getStringInput("엔터 키를 누르면 검색 결과로 돌아갑니다...");
            // 검색 폼으로 돌아가기
            showSearchForm();
        }
    }


    /**
     * 검색 결과가 없을 때 표시
     *
     * @param keyword 검색어
     */
    @Override
    public void showNoSearchResults(String keyword) {
        consoleHelper.displayHeader("🔍 메일 검색 결과");
        System.out.println("검색어: \"" + keyword + "\"");
        consoleHelper.displayDivider();

        System.out.println("→ 검색 결과가 없습니다.");
        System.out.println("→ 다른 검색어로 시도해 보세요.");

        consoleHelper.displayDivider();

        // 입력 대기
        consoleHelper.getStringInput("엔터 키를 누르면 돌아갑니다...");
    }
}