package com.metaverse.mail.dao.mock;

import com.metaverse.mail.dao.interfaces.EmailDao;
import com.metaverse.mail.model.Email;

import java.util.ArrayList;
import java.util.List;

/**
 * 테스트용 EmailDao Mock 클래스
 *
 * 실제 데이터베이스 연결 없이 테스트를 위한 더미 구현을 제공합니다.
 */
public class MockEmailDao implements EmailDao {

    @Override
    public int createEmail(Email email) {
        // 테스트를 위해 항상 고정된 ID 반환
        System.out.println("Mock: 이메일 생성 성공 - 제목: " + email.getTitle());
        return 1000; // 더미 이메일 ID
    }

    @Override
    public Email getEmailById(int emailId) {
        // 테스트용 더미 이메일 생성
        Email dummyEmail = new Email();
        dummyEmail.setEmailIdx(emailId);
        dummyEmail.setSenderId(1);
        dummyEmail.setTitle("테스트 이메일");
        dummyEmail.setBody("테스트 이메일 본문");
        dummyEmail.setStatus('Y');

        return dummyEmail;
    }

    @Override
    public List<Email> getEmailsBySenderId(int senderId) {
        // 테스트용 더미 이메일 리스트 생성
        List<Email> emails = new ArrayList<>();

        Email email1 = new Email();
        email1.setEmailIdx(1001);
        email1.setSenderId(senderId);
        email1.setTitle("첫 번째 테스트 이메일");
        email1.setBody("첫 번째 테스트 이메일 본문");
        email1.setStatus('Y');

        Email email2 = new Email();
        email2.setEmailIdx(1002);
        email2.setSenderId(senderId);
        email2.setTitle("두 번째 테스트 이메일");
        email2.setBody("두 번째 테스트 이메일 본문");
        email2.setStatus('Y');

        emails.add(email1);
        emails.add(email2);

        return emails;
    }


    @Override
    public boolean deleteEmail(int emailId) {
        // 테스트를 위해 항상 성공 반환
        System.out.println("Mock: 이메일 삭제 성공 - 이메일 ID: " + emailId);
        return true;
    }

    @Override
    public List<Email> searchEmails(String keyword, int userId) {
        // 테스트용 검색 결과 생성
        List<Email> searchResults = new ArrayList<>();

        Email email1 = new Email();
        email1.setEmailIdx(1003);
        email1.setSenderId(userId);
        email1.setTitle("검색 테스트 이메일 1");
        email1.setBody("키워드를 포함한 테스트 이메일 본문 1");
        email1.setStatus('Y');

        Email email2 = new Email();
        email2.setEmailIdx(1004);
        email2.setSenderId(userId);
        email2.setTitle("검색 테스트 이메일 2");
        email2.setBody("키워드를 포함한 테스트 이메일 본문 2");
        email2.setStatus('Y');

        searchResults.add(email1);
        searchResults.add(email2);

        return searchResults;
    }
}