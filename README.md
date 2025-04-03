# 이메일 관리 시스템 (콘솔 기반)
JDBC를 활용한 콘솔 기반 이메일 관리 시스템입니다.

## 프로젝트 목표
이 프로젝트는 JDBC를 활용하여 데이터베이스와 상호작용하는 콘솔 기반 이메일 관리 시스템을 구현합니다.
주요 기능으로 회원 관리, 이메일 작성/읽기/검색, 휴지통 관리 등이 있습니다.

## 개발 환경

- Java 17 (Eclipse Temurin 17.0.14)
- MySQL 8.4
- JDBC

## 기능 요약

### 사용자 관리

- 로그인
- 회원가입
- 프로필 수정
- 회원 탈퇴


### 이메일 기능

- 이메일 작성 및 전송
- 받은 메일함 조회
- 보낸 메일함 조회
- 이메일 검색
- 휴지통 관리



## 개발자 역할 분담

- 개발자 A (우선): 로그인/회원가입, 회원수정 메뉴
- 개발자 B (유진): 메일작성, 받은메일함, 메일검색 메뉴
- 개발자 C (효민): 보낸메일함, 휴지통 메뉴

## 설치 및 실행 방법

### 프로젝트 클론
```bash
git clone https://github.com/choe-yujin/email-management-system.git
```

### 데이터베이스 설정
- MySQL 서버에서 sql/schema.sql 스크립트를 실행하여 필요한 테이블을 생성합니다.
- sql/sample_data.sql 스크립트를 실행하여 테스트 데이터를 로드합니다.
```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/sample_data.sql
```

### 환경 설정
- resources 에 config.properties 파일을 생성 후 아래 내용을 작성합니다.
```java
- db.url=jdbc:mysql://localhost:3306/MAIL_SYSTEM
- db.username = 이름
- db.password = 비밀번호
```

### 프로젝트 컴파일 및 실행

```bash
javac -d bin src/**/*.java
java -cp bin com.metaverse.mail.Application
```

## 테이블 구조

- USER: 사용자 정보 관리
- EMAIL: 이메일 기본 정보
- EMAIL_LINK: 이메일과 수신자 연결 정보
- TRASH: 휴지통 관리

## 개발 가이드
- [Git 및 GitHub 사용 가이드](./GIT_GUIDE.md)

### 브랜치 전략

- main: 최종 배포 브랜치
- develop: 개발 통합 브랜치

### 기능별 브랜치:
```
- main           # 최종 제출용 안정 버전
|
+-- develop      # 개발 통합 브랜치
    |
    +-- feature/user       # 개발자 A(우선님) 작업 브랜치
    |   |
    |   +-- feature/user/login
    |   +-- feature/user/register
    |   +-- feature/user/profile
    |
    +-- feature/mail       # 개발자 B(유진님) 작업 브랜치
    |   |
    |   +-- feature/mail/compose
    |   +-- feature/mail/inbox
    |   +-- feature/mail/search
    |
    +-- feature/inbox      # 개발자 C(효민님) 작업 브랜치
        |
        +-- feature/inbox/sent
        +-- feature/inbox/trash
```

### 커밋 메시지 규칙

- 기능 추가: feat: 기능 설명
- 버그 수정: fix: 버그 설명
- 리팩토링: refactor: 내용
- 문서 수정: docs: 내용

## 코딩 스타일 가이드

- 모든 클래스와 메서드에 적절한 주석을 작성합니다.
- 변수와 메서드 명은 camelCase를 사용합니다.
- 클래스 명은 PascalCase를 사용합니다.
- 상수는 모두 대문자로 작성하고 단어 사이는 언더스코어(_)로 구분합니다.


## 프로젝트 구조
```plantuml
com.metaverse.mail/
├── Application.java              # 프로그램 진입점
│
├── common/                       # 공통 유틸리티
│   ├── JDBCConnection.java       # DB 연결 관리
│   ├── Constants.java            # 상수
│   ├── QueryUtil.java            # SQL 쿼리 유틸리티
│   ├── Session.java              # 로그인 세션 관리
│   └── ConsoleHelper.java        # 콘솔 UI 공통 기능
│
├── model/                        # 기본 모델 클래스
│   ├── User.java                 # 사용자 모델
│   ├── Email.java                # 이메일 모델
│   ├── EmailLink.java            # 이메일 링크 모델
│   └── Trash.java                # 휴지통 모델
│
├── dto/                          # 데이터 전송 객체 (DTO)
│   ├── user/                     # 개발자 A(우선님) 담당
│   │   ├── UserLoginDto.java     # 로그인 입출력 DTO
│   │   ├── UserRegisterDto.java  # 회원가입 입력 DTO
│   │   └── UserProfileDto.java   # 회원정보 DTO
│   │
│   ├── mail/                           # 개발자 B(유진님) 담당
│   │   ├── EmailComposeDto.java        # 메일 작성 DTO
│   │   ├── ReceivedEmailDto.java       # 받은 메일 DTO
│   │   ├── EmailSearchDto.java         # 이메일 검색 결과 DTO
│   │   ├── ReceivedEmailSearchDto.java # 수신 이메일 검색 결과 DTO
│   │   ├── SnetEmailSearchDto.java     # 발신 이메일 검색 결과 DTO
│   │   └── EmailSearchDto.java         # 메일 검색 DTO
│   │
│   └── inbox/                    # 개발자 C(효민님) 담당
│       ├── SentEmailDto.java     # 보낸 메일 DTO
│       └── TrashEmailDto.java    # 휴지통 DTO
│
├── dao/                          # 데이터 액세스 객체 (DAO)
│   ├── interfaces/               # DAO 인터페이스
│   │   ├── UserDao.java
│   │   ├── EmailDao.java
│   │   ├── EmailLinkDao.java
│   │   └── TrashDao.java
│   │
│   └── impl/                     # DAO 구현체
│       ├── user/                 # 개발자 A 담당
│       │   └── UserDaoImpl.java 
│       │
│       ├── mail/                 # 개발자 B 담당
│       │   └── EmailDaoImpl.java
│       │
│       └── inbox/                # 개발자 C 담당
│           ├── EmailLinkDaoImpl.java
│           └── TrashDaoImpl.java
│
├── service/                      # 비즈니스 로직
│   ├── interfaces/               # 서비스 인터페이스
│   │   ├── UserService.java
│   │   ├── EmailService.java
│   │   ├── InboxService.java
│   │   └── TrashService.java
│   │
│   └── impl/                     # 서비스 구현체
│       ├── user/                 # 개발자 A 담당
│       │   └── UserServiceImpl.java
│       │
│       ├── mail/                 # 개발자 B 담당
│       │   └── EmailServiceImpl.java
│       │
│       └── inbox/                # 개발자 C 담당
│           ├── InboxServiceImpl.java
│           └── TrashServiceImpl.java
│
├── view/                         # 사용자 인터페이스
│   ├── interfaces/               # View 인터페이스
│   │   ├── MainMenuView.java     # 메인 메뉴 인터페이스(공통)
│   │   │
│   │   ├── user/                 # 사용자 관련 인터페이스
│   │   │   ├── LoginView.java    # 로그인 화면 인터페이스
│   │   │   ├── RegisterView.java # 회원가입 화면 인터페이스
│   │   │   └── ProfileView.java  # 프로필 관리 화면 인터페이스
│   │   │
│   │   ├── mail/                 # 메일 관련 인터페이스
│   │   │   ├── ComposeView.java  # 메일 작성 화면 인터페이스
│   │   │   ├── InboxView.java    # 받은 메일함 화면 인터페이스
│   │   │   └── SearchView.java   # 메일 검색 화면 인터페이스
│   │   │
│   │   └── inbox/                # 보낸메일/휴지통 관련 인터페이스
│   │       ├── SentMailView.java # 보낸 메일함 화면 인터페이스
│   │       └── TrashView.java    # 휴지통 화면 인터페이스
│   │
│   └── impl/                     # View 구현체
│       ├── MainMenuViewImpl.java # 메인 메뉴 (공통)
│       │
│       ├── user/                 # 개발자 A 담당
│       │   ├── LoginViewImpl.java
│       │   ├── RegisterViewImpl.java
│       │   └── ProfileViewImpl.java
│       │
│       ├── mail/                 # 개발자 B 담당
│       │   ├── ComposeViewImpl.java
│       │   ├── InboxViewImpl.java
│       │   ├── ReplyViewImpl.java
│       │   └── SearchViewImpl.java
│       │
│       └── inbox/                # 개발자 C 담당
│           ├── SentMailViewImpl.java
│           └── TrashViewImpl.java
```


## 다이어그램

### 데이터베이스 ER 다이어그램
```mermaid
erDiagram
    USER {
        int idx PK "회원 고유 식별자"
        varchar email_id "이메일 아이디(UNIQUE)"
        varchar email_pwd "비밀번호"
        varchar nickname "닉네임"
        char status "회원 상태(A:활성, D:탈퇴)"
        datetime created_at "등록일"
        datetime updated_at "수정일"
        datetime deleted_at "삭제일"
    }
    
    EMAIL {
        int email_idx PK "이메일 고유 식별자"
        int sender_id FK "발신자 ID"
        varchar title "제목"
        longtext body "내용"
        char status "메일 발송 상태(Y:발송완료, N:발송실패)"
        datetime created_at "발신일"
    }
    
    EMAIL_LINK {
        int link_idx PK "이메일 링크 고유 식별자"
        int receiver_id FK "수신자 ID"
        int email_idx FK "이메일 ID"
        char is_readed "읽음 여부(Y:읽음, N:안읽음)"
        char is_deleted "삭제 여부(Y:삭제, N:정상)"
    }
    
    TRASH {
        int trash_idx PK "휴지통 고유 식별자"
        int link_id FK "이메일 링크 ID"
        datetime deleted_at "삭제일"
        datetime expiration_date "영구삭제 예정일(30일 후)"
        char is_restored "복구 여부(Y:복구됨, N:삭제상태)"
    }
    
    USER ||--o{ EMAIL : "sends"
    EMAIL ||--o{ EMAIL_LINK : "received_by"
    USER ||--o{ EMAIL_LINK : "receives"
    EMAIL_LINK ||--o{ TRASH : "deleted_to"
```


### 시퀀스 다이어그램(이메일 작성 및 전송 과정)
```mermaid
sequenceDiagram
    participant User as 사용자
    participant CV as ComposeViewImpl
    participant ES as EmailServiceImpl
    participant ED as EmailDaoImpl
    participant ELD as EmailLinkDaoImpl
    participant UD as UserDaoImpl
    participant DB as 데이터베이스

    User->>CV: 1. 메일 작성 요청
    CV->>CV: 메일 작성 화면 표시
    User->>CV: 수신자, 제목, 내용 입력
    CV->>ES: sendEmail(EmailComposeDto, senderId)

    ES->>UD: 2. 수신자 유효성 검사
    loop 각 수신자에 대해
        ES->>UD: findByEmailId(receiverEmail)
        UD->>DB: SELECT * FROM USER WHERE email_id = ?
        DB-->>UD: 사용자 정보 반환
        UD-->>ES: User 객체 또는 null 반환
    end

    ES->>ES: 3. 유효한 수신자 확인

    ES->>DB: 4. 트랜잭션 시작 (setAutoCommit(false))

    ES->>ED: 5. 이메일 저장
    ED->>DB: INSERT INTO EMAIL(sender_id, title, body, status, created_at)
    DB-->>ED: 생성된 이메일 ID 반환
    ED-->>ES: emailId 반환

    loop 6. 각 유효한 수신자에 대해
        ES->>ELD: createEmailLink(emailId, receiverId)
        ELD->>DB: INSERT INTO EMAIL_LINK(email_idx, receiver_id, is_readed, is_deleted)
        DB-->>ELD: 결과 반환
        ELD-->>ES: 생성 성공 여부 반환
    end

    ES->>DB: 7. 트랜잭션 커밋 (commit())
    ES-->>CV: 8. 전송 결과 반환
    CV-->>User: 9. 결과 메시지 표시
```

### 시퀀스 다이어그램(받은 메일함을 조회하는 과정)
```mermaid
sequenceDiagram
    participant User as 사용자
    participant IV as InboxViewImpl
    participant ES as EmailServiceImpl
    participant ELD as EmailLinkDaoImpl
    participant ED as EmailDaoImpl
    participant UD as UserDaoImpl
    participant DB as 데이터베이스

    User->>IV: 1. 받은 메일함 조회 요청
    IV->>ES: getReceivedEmails(userId)
    
    ES->>ELD: 2. 수신자의 이메일 링크 조회
    ELD->>DB: SELECT * FROM EMAIL_LINK WHERE receiver_id = ? AND is_deleted = 'N'
    DB-->>ELD: 이메일 링크 목록 반환
    ELD-->>ES: EmailLink 목록 반환
    
    loop 3. 각 이메일 링크에 대해
        ES->>ED: getEmailById(emailIdx)
        ED->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
        DB-->>ED: 이메일 정보 반환
        ED-->>ES: Email 객체 반환
        
        ES->>UD: findById(senderId)
        UD->>DB: SELECT * FROM USER WHERE idx = ?
        DB-->>UD: 발신자 정보 반환
        UD-->>ES: User 객체 반환
        
        ES->>ES: ReceivedEmailDto 생성 및 목록에 추가
    end
    
    ES->>ES: 4. 날짜 기준 내림차순 정렬
    ES-->>IV: 5. ReceivedEmailDto 목록 반환
    IV-->>User: 6. 받은 메일 목록 표시
    
    User->>IV: 7. 특정 이메일 상세 조회 요청
    IV->>ES: getEmailDetails(emailId, userId)
    
    ES->>ED: 8. 이메일 정보 조회
    ED->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>ED: 이메일 정보 반환
    ED-->>ES: Email 객체 반환
    
    ES->>ELD: 9. 사용자의 이메일 링크 조회
    ELD->>DB: SELECT * FROM EMAIL_LINK WHERE email_idx = ? AND receiver_id = ?
    DB-->>ELD: 이메일 링크 정보 반환
    ELD-->>ES: EmailLink 객체 반환
    
    alt 10. 이메일이 읽지 않은 상태인 경우
        ES->>ELD: markAsRead(linkId)
        ELD->>DB: UPDATE EMAIL_LINK SET is_readed = 'Y' WHERE link_idx = ?
        DB-->>ELD: 업데이트 결과 반환
        ELD-->>ES: 성공 여부 반환
    end
    
    ES->>UD: 11. 발신자 정보 조회
    UD->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>UD: 발신자 정보 반환
    UD-->>ES: User 객체 반환
    
    ES->>ES: 12. ReceivedEmailDto 생성
    ES-->>IV: 13. ReceivedEmailDto 반환
    IV-->>User: 14. 이메일 상세 내용 표시
```

### 시퀀스 다이어그램(메일을 검색하는 과정)
```mermaid
sequenceDiagram
    participant User as 사용자
    participant SV as SearchViewImpl
    participant ES as EmailServiceImpl
    participant ED as EmailDaoImpl
    participant ELD as EmailLinkDaoImpl
    participant UD as UserDaoImpl
    participant DB as 데이터베이스

    User->>SV: 1. 메일 검색 요청
    SV->>SV: 검색 화면 표시
    User->>SV: 검색 키워드 입력
    SV->>ES: searchReceivedEmails(keyword, userId)
    
    ES->>ED: 2. 수신 이메일 검색
    ED->>DB: SELECT e.*, el.* FROM EMAIL e JOIN EMAIL_LINK el ON e.email_idx = el.email_idx WHERE el.receiver_id = ? AND (e.title LIKE ? OR e.body LIKE ?) AND el.is_deleted = 'N'
    DB-->>ED: 검색 결과 반환
    ED-->>ES: Email 목록 반환
    
    loop 3. 각 이메일에 대해
        ES->>UD: findById(senderId)
        UD->>DB: SELECT * FROM USER WHERE idx = ?
        DB-->>UD: 발신자 정보 반환
        UD-->>ES: User 객체 반환
        
        ES->>ELD: getLinksByReceiverId(userId)
        ELD->>DB: SELECT * FROM EMAIL_LINK WHERE receiver_id = ? AND email_idx = ?
        DB-->>ELD: 이메일 링크 정보 반환
        ELD-->>ES: EmailLink 목록 반환
        
        ES->>ES: ReceivedEmailSearchDto 생성 및 목록에 추가
    end
    
    ES->>ES: 4. 날짜 기준 내림차순 정렬
    ES-->>SV: 5. 검색 결과 반환
    SV-->>User: 6. 검색 결과 표시
    
    User->>SV: 7. 특정 검색 결과 선택
    SV->>ES: getEmailDetails(emailId, userId)
    
    ES->>ED: 8. 이메일 정보 조회
    ED->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>ED: 이메일 정보 반환
    ED-->>ES: Email 객체 반환
    
    ES->>ELD: 9. 사용자의 이메일 링크 조회
    ELD->>DB: SELECT * FROM EMAIL_LINK WHERE email_idx = ? AND receiver_id = ?
    DB-->>ELD: 이메일 링크 정보 반환
    ELD-->>ES: EmailLink 객체 반환
    
    ES->>UD: 10. 발신자 정보 조회
    UD->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>UD: 발신자 정보 반환
    UD-->>ES: User 객체 반환
    
    ES->>ES: 11. ReceivedEmailDto 생성
    ES-->>SV: 12. ReceivedEmailDto 반환
    SV-->>User: 13. 선택한 이메일 상세 내용 표시
```