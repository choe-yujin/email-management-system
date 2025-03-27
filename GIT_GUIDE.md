# Git 및 GitHub 입문 가이드
## 이메일 관리 시스템 프로젝트를 위한 개발 프로세스

이 가이드는 Git과 GitHub 기본 사용법과 우리 팀 프로젝트의 개발 프로세스를 안내합니다.

## 목차
1. [Git과 GitHub 기본 개념](#1-git과-github-기본-개념)
2. [시작하기 전에 준비할 것](#2-시작하기-전에-준비할-것)
3. [프로젝트 복제(Clone)하기](#3-프로젝트-복제clone하기)
4. [브랜치 만들고 사용하기](#4-브랜치-만들고-사용하기)
5. [변경사항 저장하고 공유하기](#5-변경사항-저장하고-공유하기)
6. [팀 개발 워크플로우](#6-팀-개발-워크플로우)
7. [자주 사용하는 Git 명령어](#7-자주-사용하는-git-명령어)
8. [문제 해결하기](#8-문제-해결하기)

---

## 1. Git과 GitHub 기본 개념

### Git이란?
- **버전 관리 시스템**: 코드의 변경 이력을 추적하고 관리합니다.
- **분산형**: 모든 개발자가 전체 코드 이력의 복사본을 가지고 있어 인터넷 연결 없이도 작업할 수 있습니다.

### GitHub이란?
- **Git 저장소 호스팅 서비스**: Git으로 관리되는 코드를 온라인에 저장하고 공유할 수 있습니다.
- **협업 도구**: 코드 리뷰, 이슈 트래킹, 프로젝트 관리 기능을 제공합니다.

### 주요 용어
- **저장소(Repository)**: 프로젝트 파일과 그 변경 이력이 저장되는 공간
- **커밋(Commit)**: 변경사항을 저장하는 작업 단위
- **브랜치(Branch)**: 독립적인 작업 공간
- **원격 저장소(Remote)**: GitHub 같은 온라인에 있는 저장소
- **로컬 저장소(Local)**: 내 컴퓨터에 있는 저장소

---

## 2. 시작하기 전에 준비할 것

### Git 설치하기
1. [Git 다운로드 페이지](https://git-scm.com/downloads)에서 운영체제에 맞는 Git을 다운로드합니다.
2. 설치 프로그램을 실행하고 기본 옵션으로 설치합니다.

### GitHub 프로젝트 접근 권한 받기
가입한 GitHub 계정 정보를 유진에게 공유하여 프로젝트 접근 권한을 받습니다.

### Git 초기 설정하기
명령 프롬프트(윈도우) 또는 터미널(Mac/Linux)을 열고 다음 명령어를 입력합니다:

```bash
# 사용자 이름 설정
git config --global user.name "내 이름"

# 이메일 설정 (GitHub 계정과 동일한 이메일 사용)
git config --global user.email "your.email@example.com"
```

---

## 3. 프로젝트 복제(Clone)하기

1. 명령 프롬프트/터미널을 열고 프로젝트를 저장할 폴더로 이동합니다:
   ```bash
   # 예: Documents 폴더로 이동
   cd Documents
   ```

2. 프로젝트를 복제합니다:
   ```bash
   git clone https://github.com/choe-yujin/email-management-system.git
   ```

3. 복제된 프로젝트 폴더로 이동합니다:
   ```bash
   cd email-management-system
   ```

4. 프로젝트의 기본 브랜치를 확인합니다:
   ```bash
   git branch
   # 출력: * master
   ```

5. develop 브랜치로 전환합니다:
   ```bash
   git checkout develop
   ```

---

## 4. 브랜치 만들고 사용하기

### 담당 역할에 따른 브랜치 생성

우리 팀은 다음과 같이 역할이 나뉘어 있습니다:
- **개발자 A (우선)**: 로그인/회원가입, 회원수정 관련 기능
- **개발자 B (유진)**: 메일작성, 받은메일함, 메일검색 관련 기능
- **개발자 C (효민)**: 보낸메일함, 휴지통 관련 기능

각자 담당 기능에 맞는 브랜치를 만들어 작업합니다:

```bash
# 개발자 A (우선)의 경우
git checkout develop  # develop 브랜치에서 시작
git checkout -b feature/user/login  # 로그인 기능 브랜치

# 개발자 B (유진)의 경우
git checkout develop
git checkout -b feature/mail/compose  # 메일 작성 기능 브랜치

# 개발자 C (효민)의 경우
git checkout develop
git checkout -b feature/inbox/sent  # 보낸 메일함 기능 브랜치
```

### 브랜치 전환하기

작업 중인 브랜치를 다른 브랜치로 전환하려면:

```bash
git checkout 브랜치이름
# 예: git checkout feature/user/login
```

### 현재 작업 중인 브랜치 확인하기

```bash
git branch
# 현재 브랜치 앞에 * 표시가 있습니다.
```

---

## 5. 변경사항 저장하고 공유하기

### 작업 흐름

1. 코드 작성하기
2. 변경사항 확인하기
3. 변경사항 스테이징하기
4. 커밋하기
5. 원격 저장소에 푸시하기

### 단계별 명령어

#### 1. 변경사항 확인하기
```bash
git status
```

#### 2. 변경사항 스테이징하기
```bash
# 특정 파일만 스테이징
git add 파일경로
# 예: git add src/com/metaverse/mail/view/impl/user/LoginView.java

# 모든 변경된 파일 스테이징
git add .
```

#### 3. 커밋하기
```bash
git commit -m "feat: 로그인 기능 구현 완료"
```

#### 4. 원격 저장소에 푸시하기
```bash
# 첫 푸시 시 (원격 브랜치 생성)
git push -u origin 브랜치이름
# 예: git push -u origin feature/user/login

# 이후 푸시
git push
```

---

## 6. 팀 개발 워크플로우

### 1. 개발 시작 전

1. 항상 최신 develop 브랜치를 가져옵니다:
   ```bash
   git checkout develop
   git pull origin develop
   ```

2. 작업할 기능별 브랜치를 생성합니다:
   ```bash
   git checkout -b feature/기능이름
   # 예: git checkout -b feature/user/register
   ```

### 2. 기능 개발 중

1. 작은 단위로 자주 커밋합니다:
   ```bash
   git add .
   git commit -m "회원가입 유효성 검사 추가"
   ```

2. 정기적으로 원격 저장소에 푸시합니다:
   ```bash
   git push origin feature/user/register
   ```

3. 다른 개발자의 변경사항을 자주 가져옵니다:
   ```bash
   git checkout develop
   git pull origin develop
   git checkout feature/user/register
   git merge develop  # 충돌이 발생할 수 있습니다
   ```

### 3. 기능 개발 완료 후

1. 최종 테스트를 진행합니다.

2. GitHub에서 Pull Request(PR)를 생성합니다:
   - GitHub 저장소 페이지에 접속
   - 'Pull requests' 탭 클릭
   - 'New pull request' 버튼 클릭
   - 'base' 브랜치를 'develop'으로, 'compare' 브랜치를 자신의 기능 브랜치로 선택
   - 'Create pull request' 클릭
   - PR 제목과 설명 작성 후 'Create pull request' 클릭

3. 팀원들의 코드 리뷰를 기다립니다.

4. 리뷰 후 develop 브랜치에 병합됩니다 (유진 담당).

5. 새로운 기능 개발을 위해 1번부터 다시 시작합니다.

### 세부 개발 프로세스

프로젝트는 다음과 같은 단계로 진행됩니다:

1. **초기 설정**:
    * 모든 인터페이스와 모델 클래스를 정의하여 `main` 브랜치에 푸시
    * 각 개발자는 `develop` 브랜치에서 자신의 feature 브랜치 생성

2. **기능 개발**:
    * 각 개발자는 자신의 feature 브랜치에서 작업
    * 작은 단위로 커밋하고 자주 푸시

3. **코드 리뷰 및 병합**:
    * 기능 구현 완료 후 `develop` 브랜치로 Pull Request 생성
    * 팀원 코드 리뷰 후 `develop` 브랜치에 병합

4. **통합 테스트**:
    * `develop` 브랜치에서 통합 테스트 진행
    * 모든 기능이 안정적으로 동작하면 `main` 브랜치로 병합

---

## 7. 자주 사용하는 Git 명령어

### 기본 명령어

```bash
# 현재 저장소 상태 확인
git status

# 변경 이력 확인
git log
git log --oneline  # 한 줄로 요약해서 보기

# 원격 저장소에서 변경사항 가져오기
git pull

# 특정 파일 변경사항 취소하기
git checkout -- 파일명
```

### 브랜치 관련 명령어

```bash
# 브랜치 목록 보기
git branch

# 브랜치 생성하기
git branch 브랜치이름

# 브랜치 전환하기
git checkout 브랜치이름

# 브랜치 생성하고 바로 전환하기
git checkout -b 브랜치이름

# 브랜치 삭제하기 (작업 완료 후)
git branch -d 브랜치이름
```

### 병합 관련 명령어

```bash
# 다른 브랜치의 변경사항을 현재 브랜치에 병합하기
git merge 브랜치이름

# 병합 충돌 해결 후 계속하기
git add .
git commit
```

---

## 8. 문제 해결하기

### 충돌(Conflict) 해결하기

1. 충돌이 발생하면 Git은 충돌이 발생한 파일에 표시를 남깁니다:
   ```
   <<<<<<< HEAD
   현재 브랜치의 코드
   =======
   병합하려는 브랜치의 코드
   >>>>>>> feature/branch-name
   ```

2. 파일을 열어 충돌 부분을 수정합니다 (두 변경사항을 적절히 합치거나 하나를 선택).

3. 충돌 표시(`<<<<<<<`, `=======`, `>>>>>>>`)를 제거합니다.

4. 수정된 파일을 저장합니다.

5. 변경사항을 커밋합니다:
   ```bash
   git add .
   git commit -m "충돌 해결"
   ```

### 일반적인 문제 해결

1. **변경사항을 커밋하기 전에 잘못 수정한 경우**:
   ```bash
   git checkout -- 파일명
   ```

2. **마지막 커밋 메시지를 수정하고 싶은 경우**:
   ```bash
   git commit --amend -m "새로운 커밋 메시지"
   ```

3. **원격 저장소에 푸시할 때 오류가 발생하는 경우**:
   ```bash
   git pull origin 브랜치이름
   # 충돌 해결 후
   git push origin 브랜치이름
   ```

4. **특정 파일을 커밋에서 제외하고 싶은 경우**:
   ```bash
   git reset HEAD 파일명
   ```

5. **모든 변경사항을 취소하고 마지막 커밋 상태로 돌아가고 싶은 경우**:
   ```bash
   git reset --hard HEAD
   ```

### 도움 요청하기

문제가 발생하면 바로 팀원(특히 유진)에게 도움을 요청하세요. Git은 배우는 과정에서 혼란스러울 수 있으니 부담 없이 질문하세요!

---

## 추가 자료

- [Git 공식 문서](https://git-scm.com/book/ko/v2)
- [GitHub 가이드](https://guides.github.com/)
- [Git 간편 안내서](https://rogerdudler.github.io/git-guide/index.ko.html)

---

## 팀 개발 일정 및 담당 기능

### 3월27일 ~ 3월28일: 환경 설정 및 기본 구조 구현
- **모든 팀원**: Git/GitHub 설정, 프로젝트 복제, 샘플 데이터 확인

### 3월31일 ~ 4월2일: 담당 기능별 개발
- **개발자 A (우선)**: 로그인/회원가입, 회원수정 메뉴
  - `LoginView`, `RegisterView`, `UserProfileView` 구현
  - `UserDao`, `UserService` 구현

- **개발자 B (유진)**: 메일작성, 받은메일함, 메일검색 메뉴
  - `ComposeEmailView`, `InboxView`, `SearchEmailView` 구현
  - `EmailDao`, `EmailService` 구현

- **개발자 C (효민)**: 보낸메일함, 휴지통 메뉴
  - `SentEmailView`, `TrashView` 구현
  - `EmailLinkDao`, `TrashDao`, `InboxService`, `TrashService` 구현

### 4월3일: 통합 테스트 및 버그 수정
- 각 기능 간 연동 테스트
- 사용자 시나리오 테스트
- 버그 수정 및 코드 리팩토링

### 4월4일: 최종 검토 및 발표
- 최종 버그 수정
- 코드 리뷰 및 정리
- 발표

---

이 가이드에 대해 추가 질문이 있거나 Git/GitHub 사용 중 문제가 발생하면 언제든지 문의해주세요!
