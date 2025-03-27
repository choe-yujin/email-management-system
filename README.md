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
- feature/user/*: 사용자 관련 기능 (개발자 A: 우선)
- feature/mail/*: 메일 작성/수신 관련 기능 (개발자 B: 유진)
- feature/inbox/*: 발신함/휴지통 관련 기능 (개발자 C: 효민)

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
```bash
com.metaverse.mail/
├── Application.java              # 프로그램 진입점
│
├── common/                       # 공통 유틸리티
│   ├── JDBCConnection.java       # DB 연결 관리
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
│   ├── mail/                     # 개발자 B(유진님) 담당
│   │   ├── EmailComposeDto.java  # 메일 작성 DTO
│   │   ├── ReceivedEmailDto.java # 받은 메일 DTO
│   │   └── EmailSearchDto.java   # 메일 검색 DTO
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
│   │   ├── MenuView.java
│   │   ├── UserView.java
│   │   ├── EmailView.java
│   │   └── TrashView.java
│   │
│   └── impl/                     # View 구현체
│       ├── MainMenuView.java     # 메인 메뉴 (공통)
│       │
│       ├── user/                 # 개발자 A 담당
│       │   ├── LoginView.java
│       │   ├── RegisterView.java
│       │   └── UserProfileView.java
│       │
│       ├── mail/                 # 개발자 B 담당
│       │   ├── ComposeEmailView.java
│       │   ├── InboxView.java
│       │   └── SearchEmailView.java
│       │
│       └── inbox/                # 개발자 C 담당
│           ├── SentEmailView.java
│           └── TrashView.java
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

### 클래스 다이어그램
```mermaid
classDiagram
    %% 모델 클래스
    class User {
        -int idx
        -String emailId
        -String emailPwd
        -String nickname
        -char status
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        -LocalDateTime deletedAt
        +getters()
        +setters()
    }
    
    class Email {
        -int emailIdx
        -int senderId
        -String title
        -String body
        -char status
        -LocalDateTime createdAt
        +getters()
        +setters()
    }
    
    class EmailLink {
        -int linkIdx
        -int receiverId
        -int emailIdx
        -char isReaded
        -char isDeleted
        +getters()
        +setters()
    }
    
    class Trash {
        -int trashIdx
        -int linkId
        -LocalDateTime deletedAt
        -LocalDateTime expirationDate
        -char isRestored
        +getters()
        +setters()
    }
    
    %% DTO 클래스 - 사용자
    class UserLoginDto {
        -String emailId
        -String password
        +getters()
        +setters()
    }
    
    class UserRegisterDto {
        -String emailId
        -String password
        -String nickname
        +getters()
        +setters()
    }
    
    class UserProfileDto {
        -String currentPassword
        -String newPassword
        -String nickname
        +getters()
        +setters()
    }
    
    %% DTO 클래스 - 이메일
    class EmailComposeDto {
        -List~String~ receiverEmails
        -String title
        -String body
        +getters()
        +setters()
    }
    
    class ReceivedEmailDto {
        -int emailId
        -String senderName
        -String senderEmail
        -String title
        -String body
        -LocalDateTime sentDate
        -boolean read
        +getters()
        +setters()
    }
    
    class EmailSearchDto {
        -int emailId
        -String title
        -String personName
        -LocalDateTime date
        -String type
        -boolean read
        +getters()
        +setters()
    }
    
    %% DTO 클래스 - 받은/보낸 메일함
    class SentEmailDto {
        -int emailId
        -List~String~ receiverNames
        -List~String~ receiverEmails
        -String title
        -String body
        -LocalDateTime sentDate
        +getters()
        +setters()
    }
    
    class TrashEmailDto {
        -int trashId
        -int emailId
        -String title
        -String personName
        -String personEmail
        -String emailType
        -LocalDateTime deletedAt
        -LocalDateTime expirationDate
        +getters()
        +setters()
    }
    
    %% DAO 인터페이스
    class UserDao {
        <<interface>>
        +User findById(int userId)
        +User findByEmailId(String emailId)
        +boolean insert(User user)
        +boolean update(User user)
        +boolean updateStatus(int userId, char status)
    }
    
    class EmailDao {
        <<interface>>
        +int createEmail(Email email)
        +Email getEmailById(int emailId)
        +List~Email~ getEmailsBySenderId(int senderId)
        +boolean updateEmail(Email email)
        +boolean deleteEmail(int emailId)
        +List~Email~ searchEmails(String keyword, int userId)
    }
    
    class EmailLinkDao {
        <<interface>>
        +boolean createEmailLink(int emailId, int receiverId)
        +EmailLink getLinkById(int linkId)
        +List~EmailLink~ getLinksByReceiverId(int receiverId)
        +boolean markAsRead(int linkId)
        +boolean markAsDeleted(int linkId)
        +boolean restoreEmail(int linkId)
    }
    
    class TrashDao {
        <<interface>>
        +int createTrashItem(int linkId)
        +Trash getTrashById(int trashId)
        +List~Trash~ getTrashItemsByUserId(int userId)
        +boolean markAsRestored(int trashId)
        +boolean deleteTrash(int trashId)
        +List~Trash~ getExpiredTrashItems()
    }
    
    %% 서비스 인터페이스
    class UserService {
        <<interface>>
        +boolean register(UserRegisterDto userDto)
        +User login(UserLoginDto loginDto)
        +boolean updateProfile(UserProfileDto profileDto, int userId)
        +boolean changePassword(String oldPassword, String newPassword, int userId)
        +boolean deactivateAccount(int userId)
    }
    
    class EmailService {
        <<interface>>
        +boolean sendEmail(EmailComposeDto emailDto, int senderId)
        +List~ReceivedEmailDto~ getReceivedEmails(int userId)
        +ReceivedEmailDto getEmailDetails(int emailId, int userId)
        +List~EmailSearchDto~ searchEmails(String keyword, int userId)
        +boolean replyToEmail(int originalEmailId, String replyContent, int senderId)
    }
    
    class InboxService {
        <<interface>>
        +List~SentEmailDto~ getSentEmails(int userId)
        +SentEmailDto getSentEmailDetails(int emailId)
        +boolean deleteReceivedEmail(int emailId, int userId)
        +boolean deleteSentEmail(int emailId)
    }
    
    class TrashService {
        <<interface>>
        +List~TrashEmailDto~ getTrashEmails(int userId)
        +TrashEmailDto getTrashEmailDetails(int trashId)
        +boolean restoreEmail(int trashId)
        +boolean permanentlyDeleteEmail(int trashId)
        +int cleanupExpiredEmails()
    }
    
    %% 뷰 인터페이스
    class MenuView {
        <<interface>>
        +void showMenu()
        +void processUserChoice(int choice)
    }
    
    class UserView {
        <<interface>>
        +void showLoginForm()
        +void showRegistrationForm()
        +void showProfileManagement()
    }
    
    class EmailView {
        <<interface>>
        +void showComposeForm()
        +void showEmailList(List emails)
        +void showEmailDetails(Object email)
        +void showSearchForm()
    }
    
    class TrashView {
        <<interface>>
        +void showTrashList()
        +void showTrashDetails(TrashEmailDto email)
    }
    
    %% 구현 클래스 - DAO
    class UserDaoImpl {
        -Connection conn
        +User findById(int userId)
        +User findByEmailId(String emailId)
        +boolean insert(User user)
        +boolean update(User user)
        +boolean updateStatus(int userId, char status)
    }
    
    class EmailDaoImpl {
        -Connection conn
        +int createEmail(Email email)
        +Email getEmailById(int emailId)
        +List~Email~ getEmailsBySenderId(int senderId)
        +boolean updateEmail(Email email)
        +boolean deleteEmail(int emailId)
        +List~Email~ searchEmails(String keyword, int userId)
    }
    
    class EmailLinkDaoImpl {
        -Connection conn
        +boolean createEmailLink(int emailId, int receiverId)
        +EmailLink getLinkById(int linkId)
        +List~EmailLink~ getLinksByReceiverId(int receiverId)
        +boolean markAsRead(int linkId)
        +boolean markAsDeleted(int linkId)
        +boolean restoreEmail(int linkId)
    }
    
    class TrashDaoImpl {
        -Connection conn
        +int createTrashItem(int linkId)
        +Trash getTrashById(int trashId)
        +List~Trash~ getTrashItemsByUserId(int userId)
        +boolean markAsRestored(int trashId)
        +boolean deleteTrash(int trashId)
        +List~Trash~ getExpiredTrashItems()
    }
    
    %% 구현 클래스 - 서비스
    class UserServiceImpl {
        -UserDao userDao
        +boolean register(UserRegisterDto userDto)
        +User login(UserLoginDto loginDto)
        +boolean updateProfile(UserProfileDto profileDto, int userId)
        +boolean changePassword(String oldPassword, String newPassword, int userId)
        +boolean deactivateAccount(int userId)
        -boolean isValidEmail(String email)
        -boolean isValidPassword(String password)
    }
    
    class EmailServiceImpl {
        -EmailDao emailDao
        -EmailLinkDao emailLinkDao
        -UserDao userDao
        +boolean sendEmail(EmailComposeDto emailDto, int senderId)
        +List~ReceivedEmailDto~ getReceivedEmails(int userId)
        +ReceivedEmailDto getEmailDetails(int emailId, int userId)
        +List~EmailSearchDto~ searchEmails(String keyword, int userId)
        +boolean replyToEmail(int originalEmailId, String replyContent, int senderId)
        -Email convertToEmail(EmailComposeDto dto, int senderId)
        -ReceivedEmailDto convertToReceivedEmailDto(Email email, User sender)
    }
    
    class InboxServiceImpl {
        -EmailDao emailDao
        -EmailLinkDao emailLinkDao
        -UserDao userDao
        -TrashDao trashDao
        +List~SentEmailDto~ getSentEmails(int userId)
        +SentEmailDto getSentEmailDetails(int emailId)
        +boolean deleteReceivedEmail(int emailId, int userId)
        +boolean deleteSentEmail(int emailId)
        -SentEmailDto convertToSentEmailDto(Email email, List~User~ receivers)
    }
    
    class TrashServiceImpl {
        -TrashDao trashDao
        -EmailLinkDao emailLinkDao
        -EmailDao emailDao
        -UserDao userDao
        +List~TrashEmailDto~ getTrashEmails(int userId)
        +TrashEmailDto getTrashEmailDetails(int trashId)
        +boolean restoreEmail(int trashId)
        +boolean permanentlyDeleteEmail(int trashId)
        +int cleanupExpiredEmails()
        -TrashEmailDto convertToTrashEmailDto(Trash trash, Email email, User person)
    }
    
    %% 구현 클래스 - 뷰
    class MainMenuView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -Session session
        +void showMainMenu()
        +void showLoginMenu()
        -void processMainMenuChoice(int choice)
    }
    
    class LoginView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -UserService userService
        -Session session
        +void showLoginForm()
        -User authenticateUser(String emailId, String password)
    }
    
    class RegisterView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -UserService userService
        +void showRegistrationForm()
    }
    
    class UserProfileView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -UserService userService
        -Session session
        +void showProfileManagement()
        -void showNicknameUpdateForm()
        -void showPasswordUpdateForm()
        -void showDeactivateAccountForm()
    }
    
    class ComposeEmailView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -EmailService emailService
        -Session session
        +void showComposeForm()
        -boolean validateReceiverEmails(List~String~ emails)
    }
    
    class InboxView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -EmailService emailService
        -Session session
        +void showReceivedEmails()
        +void showEmailDetails(int emailId)
        -void showReplyForm(int emailId)
        -void processEmailOptions(int emailId, int choice)
    }
    
    class SearchEmailView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -EmailService emailService
        -Session session
        +void showSearchForm()
        +void showSearchResults(List~EmailSearchDto~ results)
        +void showEmailDetails(int emailId, String type)
    }
    
    class SentEmailView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -InboxService inboxService
        -Session session
        +void showSentEmails()
        +void showEmailDetails(int emailId)
        -void processEmailOptions(int emailId, int choice)
    }
    
    class TrashView {
        -Scanner scanner
        -ConsoleHelper consoleHelper
        -TrashService trashService
        -Session session
        +void showTrashList()
        +void showTrashDetails(int trashId)
        -void processTrashOptions(int trashId, int choice)
    }
    
    %% 공통 유틸리티 클래스
    class JDBCConnection {
        -static Connection connection
        +static Connection getConnection()
        -static void loadProperties()
        +static void closeConnection()
    }
    
    class QueryUtil {
        -static Properties queries
        +static String getQuery(String queryId)
        -static void loadQueries()
    }
    
    class Session {
        -static User currentUser
        +static void setCurrentUser(User user)
        +static User getCurrentUser()
        +static boolean isLoggedIn()
        +static void logout()
        +static int getUserId()
    }
    
    class ConsoleHelper {
        -Scanner scanner
        +void displayHeader(String title)
        +void displayDivider()
        +int getIntInput(String prompt, int min, int max)
        +String getStringInput(String prompt)
        +String getPasswordInput(String prompt)
        +void displayMenu(String title, List~String~ options)
    }
    
    %% 애플리케이션 메인 클래스
    class Application {
        +static void main(String[] args)
        -static void initializeDatabase()
        -static void showWelcomeMessage()
    }
    
    %% 관계 정의
    %% 모델 관계
    User -- Email : sender
    Email -- EmailLink : has
    User -- EmailLink : receiver
    EmailLink -- Trash : deleted
    
    %% DAO 관계
    UserDao <|.. UserDaoImpl : implements
    EmailDao <|.. EmailDaoImpl : implements
    EmailLinkDao <|.. EmailLinkDaoImpl : implements
    TrashDao <|.. TrashDaoImpl : implements
    
    %% 서비스 관계
    UserService <|.. UserServiceImpl : implements
    EmailService <|.. EmailServiceImpl : implements
    InboxService <|.. InboxServiceImpl : implements
    TrashService <|.. TrashServiceImpl : implements
    
    UserServiceImpl --> UserDao : uses
    EmailServiceImpl --> EmailDao : uses
    EmailServiceImpl --> EmailLinkDao : uses
    EmailServiceImpl --> UserDao : uses
    InboxServiceImpl --> EmailDao : uses
    InboxServiceImpl --> EmailLinkDao : uses
    InboxServiceImpl --> UserDao : uses
    InboxServiceImpl --> TrashDao : uses
    TrashServiceImpl --> TrashDao : uses
    TrashServiceImpl --> EmailLinkDao : uses
    TrashServiceImpl --> EmailDao : uses
    TrashServiceImpl --> UserDao : uses
    
    %% 뷰 관계
    MenuView <|.. MainMenuView : implements
    UserView <|.. LoginView : implements
    UserView <|.. RegisterView : implements
    UserView <|.. UserProfileView : implements
    EmailView <|.. ComposeEmailView : implements
    EmailView <|.. InboxView : implements
    EmailView <|.. SearchEmailView : implements
    EmailView <|.. SentEmailView : implements
    TrashView <|.. TrashView : implements
    
    %% 뷰-서비스 관계
    LoginView --> UserService : uses
    RegisterView --> UserService : uses
    UserProfileView --> UserService : uses
    ComposeEmailView --> EmailService : uses
    InboxView --> EmailService : uses
    SearchEmailView --> EmailService : uses
    SentEmailView --> InboxService : uses
    TrashView --> TrashService : uses
    
    %% 유틸리티 관계
    UserDaoImpl --> JDBCConnection : uses
    EmailDaoImpl --> JDBCConnection : uses
    EmailLinkDaoImpl --> JDBCConnection : uses
    TrashDaoImpl --> JDBCConnection : uses
    
    UserDaoImpl --> QueryUtil : uses
    EmailDaoImpl --> QueryUtil : uses
    EmailLinkDaoImpl --> QueryUtil : uses
    TrashDaoImpl --> QueryUtil : uses
    
    MainMenuView --> Session : uses
    LoginView --> Session : uses
    UserProfileView --> Session : uses
    ComposeEmailView --> Session : uses
    InboxView --> Session : uses
    SearchEmailView --> Session : uses
    SentEmailView --> Session : uses
    TrashView --> Session : uses
    
    MainMenuView --> ConsoleHelper : uses
    LoginView --> ConsoleHelper : uses
    RegisterView --> ConsoleHelper : uses
    UserProfileView --> ConsoleHelper : uses
    ComposeEmailView --> ConsoleHelper : uses
    InboxView --> ConsoleHelper : uses
    SearchEmailView --> ConsoleHelper : uses
    SentEmailView --> ConsoleHelper : uses
    TrashView --> ConsoleHelper : uses
    
    Application --> MainMenuView : creates
```

### 시퀀스 다이어그램
```mermaid
sequenceDiagram
    participant User as 사용자
    participant View as 뷰 (UI)
    participant Service as 서비스 계층
    participant DAO as DAO 계층
    participant DB as 데이터베이스
    
    %% 1. 회원가입 프로세스
    User->>View: 회원가입 요청
    View->>Service: register(UserRegisterDto)
    Service->>DAO: findByEmailId(email)
    DAO->>DB: SELECT * FROM USER WHERE email_id = ?
    DB-->>DAO: 결과 반환 (중복 검사)
    Service->>Service: 입력값 유효성 검증
    Service->>DAO: insert(User)
    DAO->>DB: INSERT INTO USER (email_id, email_pwd, nickname, ...)
    DB-->>DAO: 결과 반환
    DAO-->>Service: 결과 반환
    Service-->>View: 등록 성공/실패 반환
    View-->>User: 결과 표시

    %% 2. 로그인 프로세스
    User->>View: 로그인 요청
    View->>Service: login(UserLoginDto)
    Service->>DAO: findByEmailId(email)
    DAO->>DB: SELECT * FROM USER WHERE email_id = ?
    DB-->>DAO: 사용자 정보 반환
    DAO-->>Service: User 객체 반환
    Service->>Service: 비밀번호 검증
    Service-->>View: 인증된 User 객체 또는 null 반환
    View-->>User: 결과 표시 (성공 시 메인 메뉴로 이동)

    %% 3. 회원 정보 수정 프로세스
    User->>View: 프로필 수정 요청
    View->>Service: updateProfile(UserProfileDto, userId)
    Service->>DAO: findById(userId)
    DAO->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>DAO: 사용자 정보 반환
    DAO-->>Service: User 객체 반환
    Service->>Service: 현재 비밀번호 확인 및 유효성 검사
    Service->>DAO: update(User)
    DAO->>DB: UPDATE USER SET nickname = ?, updated_at = ? WHERE idx = ?
    DB-->>DAO: 결과 반환
    DAO-->>Service: 결과 반환
    Service-->>View: 수정 성공/실패 반환
    View-->>User: 결과 표시

    %% 4. 비밀번호 변경 프로세스
    User->>View: 비밀번호 변경 요청
    View->>Service: changePassword(oldPassword, newPassword, userId)
    Service->>DAO: findById(userId)
    DAO->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>DAO: 사용자 정보 반환
    DAO-->>Service: User 객체 반환
    Service->>Service: 현재 비밀번호 일치 확인 및 새 비밀번호 유효성 검사
    Service->>DAO: update(User)
    DAO->>DB: UPDATE USER SET email_pwd = ?, updated_at = ? WHERE idx = ?
    DB-->>DAO: 결과 반환
    DAO-->>Service: 결과 반환
    Service-->>View: 변경 성공/실패 반환
    View-->>User: 결과 표시

    %% 5. 회원 탈퇴 프로세스
    User->>View: 회원 탈퇴 요청
    View->>Service: deactivateAccount(userId)
    Service->>DAO: findById(userId)
    DAO->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>DAO: 사용자 정보 반환
    DAO-->>Service: User 객체 반환
    Service->>DAO: updateStatus(userId, 'D')
    DAO->>DB: UPDATE USER SET status = 'D', deleted_at = ? WHERE idx = ?
    DB-->>DAO: 결과 반환
    DAO-->>Service: 결과 반환
    Service-->>View: 탈퇴 성공/실패 반환
    View-->>User: 결과 표시 (성공 시 로그인 화면으로 이동)

    %% 6. 이메일 작성 프로세스
    User->>View: 이메일 작성 및 전송
    View->>Service: sendEmail(EmailComposeDto, senderId)
    Service->>Service: 수신자 및 내용 유효성 검사
    Service->>DAO: createEmail(Email)
    DAO->>DB: INSERT INTO EMAIL (sender_id, title, body, status, created_at)
    DB-->>DAO: 생성된 이메일 ID 반환
    
    loop 각 수신자에 대해
        Service->>DAO: findUserByEmailId(receiverEmail)
        DAO->>DB: SELECT * FROM USER WHERE email_id = ?
        DB-->>DAO: 수신자 정보 반환
        Service->>DAO: createEmailLink(emailId, receiverId)
        DAO->>DB: INSERT INTO EMAIL_LINK (email_idx, receiver_id, is_readed, is_deleted)
        DB-->>DAO: 결과 반환
    end
    
    DAO-->>Service: 결과 반환
    Service-->>View: 전송 결과 반환
    View-->>User: 전송 완료 메시지 표시

    %% 7. 받은 메일함 조회 프로세스
    User->>View: 받은 메일함 조회 요청
    View->>Service: getReceivedEmails(userId)
    Service->>DAO: getLinksByReceiverId(userId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE receiver_id = ? AND is_deleted = 'N'
    DB-->>DAO: 이메일 링크 목록 반환
    
    loop 각 이메일 링크에 대해
        DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
        DB-->>DAO: 이메일 정보 반환
        DAO->>DB: SELECT * FROM USER WHERE idx = ? (발신자 정보)
        DB-->>DAO: 발신자 정보 반환
    end
    
    DAO-->>Service: 이메일 정보 목록 반환
    Service->>Service: ReceivedEmailDto로 변환 및 정렬
    Service-->>View: ReceivedEmailDto 목록 반환
    View-->>User: 받은 메일 목록 표시

    %% 8. 이메일 상세 조회 프로세스
    User->>View: 이메일 선택
    View->>Service: getEmailDetails(emailId, userId)
    Service->>DAO: getLinkByEmailIdAndReceiverId(emailId, userId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE email_idx = ? AND receiver_id = ?
    DB-->>DAO: 이메일 링크 반환
    
    alt 읽지 않은 이메일인 경우
        Service->>DAO: markAsRead(linkId)
        DAO->>DB: UPDATE EMAIL_LINK SET is_readed = 'Y' WHERE link_idx = ?
        DB-->>DAO: 업데이트 결과 반환
    end
    
    Service->>DAO: getEmailById(emailId)
    DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>DAO: 이메일 정보 반환
    
    Service->>DAO: getUserById(senderId)
    DAO->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>DAO: 발신자 정보 반환
    
    DAO-->>Service: 필요한 정보 반환
    Service->>Service: DTO 변환
    Service-->>View: ReceivedEmailDto 반환
    View-->>User: 이메일 상세 내용 표시

    %% 9. 이메일 답장 프로세스
    User->>View: 답장 작성 요청
    View->>Service: replyToEmail(originalEmailId, replyContent, senderId)
    Service->>DAO: getEmailById(originalEmailId)
    DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>DAO: 원본 이메일 정보 반환
    
    Service->>DAO: getUserById(senderId) // 원본 발신자 = 답장 수신자
    DAO->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>DAO: 수신자 정보 반환
    
    Service->>Service: 답장 이메일 생성 ("Re: " + 원본 제목)
    Service->>DAO: createEmail(Email)
    DAO->>DB: INSERT INTO EMAIL (sender_id, title, body, status, created_at)
    DB-->>DAO: 생성된 이메일 ID 반환
    
    Service->>DAO: createEmailLink(emailId, originalSenderId)
    DAO->>DB: INSERT INTO EMAIL_LINK (email_idx, receiver_id, is_readed, is_deleted)
    DB-->>DAO: 결과 반환
    
    DAO-->>Service: 결과 반환
    Service-->>View: 답장 발송 결과 반환
    View-->>User: 답장 발송 완료 메시지 표시

    %% 10. 보낸 메일함 조회 프로세스
    User->>View: 보낸 메일함 조회 요청
    View->>Service: getSentEmails(userId)
    Service->>DAO: getEmailsBySenderId(userId)
    DAO->>DB: SELECT * FROM EMAIL WHERE sender_id = ? ORDER BY created_at DESC
    DB-->>DAO: 이메일 목록 반환
    
    loop 각 이메일에 대해
        DAO->>DB: SELECT * FROM EMAIL_LINK WHERE email_idx = ?
        DB-->>DAO: 이메일 링크 목록 반환
        
        loop 각 링크에 대해
            DAO->>DB: SELECT * FROM USER WHERE idx = ? (수신자 정보)
            DB-->>DAO: 수신자 정보 반환
        end
    end
    
    DAO-->>Service: 이메일 및 수신자 정보 반환
    Service->>Service: SentEmailDto로 변환
    Service-->>View: SentEmailDto 목록 반환
    View-->>User: 보낸 메일 목록 표시

    %% 11. 보낸 메일 상세 조회 프로세스
    User->>View: 보낸 이메일 선택
    View->>Service: getSentEmailDetails(emailId)
    Service->>DAO: getEmailById(emailId)
    DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>DAO: 이메일 정보 반환
    
    Service->>DAO: getLinksByEmailId(emailId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE email_idx = ?
    DB-->>DAO: 이메일 링크 목록 반환
    
    loop 각 링크에 대해
        DAO->>DB: SELECT * FROM USER WHERE idx = ? (수신자 정보)
        DB-->>DAO: 수신자 정보 반환
    end
    
    DAO-->>Service: 이메일 및 수신자 정보 반환
    Service->>Service: SentEmailDto로 변환
    Service-->>View: SentEmailDto 반환
    View-->>User: 보낸 메일 상세 내용 표시

    %% 12. 이메일 검색 프로세스
    User->>View: 이메일 검색 요청
    View->>Service: searchEmails(keyword, userId)
    
    Service->>DAO: searchInReceivedEmails(keyword, userId)
    DAO->>DB: SELECT * FROM EMAIL_LINK el JOIN EMAIL e ON el.email_idx = e.email_idx WHERE el.receiver_id = ? AND (e.title LIKE ? OR e.body LIKE ?) AND el.is_deleted = 'N'
    DB-->>DAO: 받은 이메일 검색 결과 반환
    
    Service->>DAO: searchInSentEmails(keyword, userId)
    DAO->>DB: SELECT * FROM EMAIL WHERE sender_id = ? AND (title LIKE ? OR body LIKE ?)
    DB-->>DAO: 보낸 이메일 검색 결과 반환
    
    DAO-->>Service: 검색 결과 반환
    Service->>Service: EmailSearchDto로 변환 및 통합
    Service-->>View: EmailSearchDto 목록 반환
    View-->>User: 검색 결과 표시

    %% 13. 받은 이메일 삭제 프로세스
    User->>View: 받은 이메일 삭제 요청
    View->>Service: deleteReceivedEmail(emailId, userId)
    Service->>DAO: getLinkByEmailIdAndReceiverId(emailId, userId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE email_idx = ? AND receiver_id = ?
    DB-->>DAO: 이메일 링크 반환
    
    Service->>DAO: markAsDeleted(linkId)
    DAO->>DB: UPDATE EMAIL_LINK SET is_deleted = 'Y' WHERE link_idx = ?
    DB-->>DAO: 업데이트 결과 반환
    
    Service->>DAO: createTrashItem(linkId)
    DAO->>DB: INSERT INTO TRASH (link_id, deleted_at, expiration_date, is_restored)
    DB-->>DAO: 생성 결과 반환
    
    DAO-->>Service: 결과 반환
    Service-->>View: 삭제 결과 반환
    View-->>User: 삭제 완료 메시지 표시

    %% 14. 보낸 이메일 삭제 프로세스
    User->>View: 보낸 이메일 삭제 요청
    View->>Service: deleteSentEmail(emailId)
    Service->>DAO: getEmailById(emailId)
    DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>DAO: 이메일 정보 반환
    
    Service->>DAO: markAsDeleted(emailId)
    DAO->>DB: UPDATE EMAIL SET status = 'D' WHERE email_idx = ?
    DB-->>DAO: 업데이트 결과 반환
    
    Service->>DAO: createTrashItemForSentEmail(emailId)
    DAO->>DB: INSERT INTO TRASH (email_idx, deleted_at, expiration_date, is_restored)
    DB-->>DAO: 생성 결과 반환
    
    DAO-->>Service: 결과 반환
    Service-->>View: 삭제 결과 반환
    View-->>User: 삭제 완료 메시지 표시

    %% 15. 휴지통 이메일 목록 조회 프로세스
    User->>View: 휴지통 조회 요청
    View->>Service: getTrashEmails(userId)
    Service->>DAO: getTrashItemsByUserId(userId)
    DAO->>DB: SELECT * FROM TRASH t JOIN EMAIL_LINK el ON t.link_id = el.link_idx WHERE el.receiver_id = ? AND t.is_restored = 'N'
    DB-->>DAO: 휴지통 항목 목록 반환
    
    loop 각 휴지통 항목에 대해
        DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = el.email_idx
        DB-->>DAO: 이메일 정보 반환
        DAO->>DB: SELECT * FROM USER WHERE idx = e.sender_id
        DB-->>DAO: 발신자 정보 반환
    end
    
    DAO-->>Service: 휴지통 이메일 정보 반환
    Service->>Service: TrashEmailDto로 변환
    Service-->>View: TrashEmailDto 목록 반환
    View-->>User: 휴지통 이메일 목록 표시

    %% 16. 휴지통 이메일 상세 조회 프로세스
    User->>View: 휴지통 이메일 선택
    View->>Service: getTrashEmailDetails(trashId)
    Service->>DAO: getTrashById(trashId)
    DAO->>DB: SELECT * FROM TRASH WHERE trash_idx = ?
    DB-->>DAO: 휴지통 항목 정보 반환
    
    Service->>DAO: getLinkById(linkId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE link_idx = ?
    DB-->>DAO: 이메일 링크 정보 반환
    
    Service->>DAO: getEmailById(emailId)
    DAO->>DB: SELECT * FROM EMAIL WHERE email_idx = ?
    DB-->>DAO: 이메일 정보 반환
    
    Service->>DAO: getUserById(senderId or receiverId)
    DAO->>DB: SELECT * FROM USER WHERE idx = ?
    DB-->>DAO: 사용자 정보 반환
    
    DAO-->>Service: 필요한 정보 반환
    Service->>Service: TrashEmailDto로 변환
    Service-->>View: TrashEmailDto 반환
    View-->>User: 휴지통 이메일 상세 내용 표시

    %% 17. 휴지통 이메일 복구 프로세스
    User->>View: 이메일 복구 요청
    View->>Service: restoreEmail(trashId)
    Service->>DAO: getTrashById(trashId)
    DAO->>DB: SELECT * FROM TRASH WHERE trash_idx = ?
    DB-->>DAO: 휴지통 항목 정보 반환
    
    Service->>DAO: getLinkById(linkId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE link_idx = ?
    DB-->>DAO: 이메일 링크 정보 반환
    
    Service->>DAO: restoreEmailLink(linkId)
    DAO->>DB: UPDATE EMAIL_LINK SET is_deleted = 'N' WHERE link_idx = ?
    DB-->>DAO: 업데이트 결과 반환
    
    Service->>DAO: markTrashAsRestored(trashId)
    DAO->>DB: UPDATE TRASH SET is_restored = 'Y' WHERE trash_idx = ?
    DB-->>DAO: 업데이트 결과 반환
    
    DAO-->>Service: 결과 반환
    Service-->>View: 복구 결과 반환
    View-->>User: 복구 완료 메시지 표시

    %% 18. 휴지통 이메일 영구 삭제 프로세스
    User->>View: 이메일 영구 삭제 요청
    View->>Service: permanentlyDeleteEmail(trashId)
    Service->>DAO: getTrashById(trashId)
    DAO->>DB: SELECT * FROM TRASH WHERE trash_idx = ?
    DB-->>DAO: 휴지통 항목 정보 반환
    
    Service->>DAO: getLinkById(linkId)
    DAO->>DB: SELECT * FROM EMAIL_LINK WHERE link_idx = ?
    DB-->>DAO: 이메일 링크 정보 반환
    
    alt 이메일 영구 삭제 (모든 링크가 삭제된 경우)
        Service->>DAO: checkIfAllLinksDeleted(emailId)
        DAO->>DB: SELECT COUNT(*) FROM EMAIL_LINK WHERE email_idx = ? AND is_deleted = 'N'
        DB-->>DAO: 결과 반환
        
        alt 모든 링크가 삭제된 경우
            Service->>DAO: deleteEmail(emailId)
            DAO->>DB: DELETE FROM EMAIL WHERE email_idx = ?
            DB-->>DAO: 삭제 결과 반환
        end
    end
    
    Service->>DAO: deleteEmailLink(linkId)
    DAO->>DB: DELETE FROM EMAIL_LINK WHERE link_idx = ?
    DB-->>DAO: 삭제 결과 반환
    
    Service->>DAO: deleteTrash(trashId)
    DAO->>DB: DELETE FROM TRASH WHERE trash_idx = ?
    DB-->>DAO: 삭제 결과 반환
    
    DAO-->>Service: 결과 반환
    Service-->>View: 영구 삭제 결과 반환
    View-->>User: 영구 삭제 완료 메시지 표시

    %% 19. 만료된 이메일 자동 삭제 프로세스 (시스템 스케줄러)
    Note over Service: 시스템 스케줄러에 의해 주기적으로 실행
    Service->>DAO: getExpiredTrashItems()
    DAO->>DB: SELECT * FROM TRASH WHERE expiration_date < NOW() AND is_restored = 'N'
    DB-->>DAO: 만료된 휴지통 항목 목록 반환
    
    loop 각 만료된 항목에 대해
        Service->>DAO: getLinkById(linkId)
        DAO->>DB: SELECT * FROM EMAIL_LINK WHERE link_idx = ?
        DB-->>DAO: 이메일 링크 정보 반환
        
        Service->>DAO: checkIfAllLinksDeleted(emailId)
        DAO->>DB: SELECT COUNT(*) FROM EMAIL_LINK WHERE email_idx = ? AND is_deleted = 'N'
        DB-->>DAO: 결과 반환
        
        alt 모든 링크가 삭제된 경우
            Service->>DAO: deleteEmail(emailId)
            DAO->>DB: DELETE FROM EMAIL WHERE email_idx = ?
            DB-->>DAO: 삭제 결과 반환
        end
        
        Service->>DAO: deleteEmailLink(linkId)
        DAO->>DB: DELETE FROM EMAIL_LINK WHERE link_idx = ?
        DB-->>DAO: 삭제 결과 반환
        
        Service->>DAO: deleteTrash(trashId)
        DAO->>DB: DELETE FROM TRASH WHERE trash_idx = ?
        DB-->>DAO: 삭제 결과 반환
    end
    
    DAO-->>Service: 처리된 항목 수 반환

    %% 20. 로그아웃 프로세스
    User->>View: 로그아웃 요청
    View->>Service: logout()
    Service->>Service: 세션 정보 삭제
    Service-->>View: 로그아웃 완료
    View-->>User: 로그인 화면으로 이동

```
