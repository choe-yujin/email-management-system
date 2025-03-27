CREATE DATABASE Mail_System;

USE Mail_System;

-- USER Table
CREATE TABLE USER
(
    idx        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '회원 고유의 idx값',
    email_id   VARCHAR(100)           NOT NULL UNIQUE COMMENT '회원 이메일 아이디',
    email_pwd  VARCHAR(255)           NOT NULL COMMENT '회원 비밀번호',
    nickname   VARCHAR(30)            NOT NULL COMMENT '회원 닉네임',
    status     CHAR(1)  DEFAULT 'A'   NOT NULL COMMENT '회원 상태(A:활성화, D:탈퇴)',
    created_at DATETIME DEFAULT NOW() NOT NULL COMMENT '회원 등록일',
    updated_at DATETIME COMMENT '회원 수정일',
    deleted_at DATETIME COMMENT '회원 삭제일',
    CONSTRAINT chk_user_status CHECK (status IN ('A', 'D')) -- A(활성회원), D(탈퇴)
);

-- EMAIL Table
CREATE TABLE EMAIL
(
    email_idx  INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '이메일 고유의 idx 값',
    sender_id  INTEGER                NOT NULL COMMENT '발신자 ID',
    title      VARCHAR(100)           NOT NULL COMMENT '제목',
    body       LONGTEXT               NOT NULL COMMENT '내용',
    status     CHAR(1)  DEFAULT 'N'   NOT NULL COMMENT '메일 발송 상태 확인(Y:발송완료, N:발송실패)',
    created_at DATETIME DEFAULT NOW() NOT NULL COMMENT '메일 발신일 표시',
    FOREIGN KEY (sender_id) REFERENCES USER (idx) ON DELETE CASCADE,
    CONSTRAINT chk_email_status CHECK (status IN ('Y', 'N'))
);

-- EMAIL_LINK Table
CREATE TABLE EMAIL_LINK
(
    link_idx    INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '이메일 링크드 고유의 idx 값',
    receiver_id INTEGER             NOT NULL COMMENT '수신자ID',
    email_idx   INTEGER             NOT NULL COMMENT '이메일 고유의 idx 값',
    is_readed   CHAR(1) DEFAULT 'N' NOT NULL COMMENT '메일 읽음 여부(Y:읽음, N:안읽음)',
    is_deleted  CHAR(1) DEFAULT 'N' NOT NULL COMMENT '메일 삭제 여부(Y:삭제, N:정상)',
    FOREIGN KEY (receiver_id) REFERENCES USER (idx) ON DELETE CASCADE,
    FOREIGN KEY (email_idx) REFERENCES EMAIL (email_idx) ON DELETE CASCADE,
    CONSTRAINT chk_email_link_read CHECK (is_readed IN ('Y', 'N')),
    CONSTRAINT chk_email_link_deleted CHECK (is_deleted IN ('Y', 'N'))
);

-- TRASH Table
CREATE TABLE TRASH
(
    trash_idx       INTEGER PRIMARY KEY AUTO_INCREMENT COMMENT '휴지통 고유의 idx 값',
    link_id         INTEGER                                             NOT NULL COMMENT '링크드 아이디(FK)',
    deleted_at      DATETIME DEFAULT NOW()                              NOT NULL COMMENT '메일 삭제일',
    expiration_date DATETIME DEFAULT (DATE_ADD(NOW(), INTERVAL 30 DAY)) NOT NULL COMMENT '메일 영구삭제 예정일(30일 후)',
    is_restored     CHAR(1)  DEFAULT 'N'                                NOT NULL COMMENT '상태(Y:복구됨, N:삭제상태)',
    FOREIGN KEY (link_id) REFERENCES EMAIL_LINK (link_idx) ON DELETE CASCADE,
    CONSTRAINT chk_trash_restored CHECK (is_restored IN ('Y', 'N'))
);