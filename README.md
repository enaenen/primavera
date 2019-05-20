# Primavera
스프링부트를 이용한 커뮤니티 사이트 개발

## Technical Specification
* Java 12 (Switch expressions)
* IntelliJ IDEA (2019.1)
* MariaDB 10.3.14
* Springboot 2.1.4.RELEASE
* Thymeleaf
* Mybatis
* Lombok
* Bootstrap

## Requirements Specification
* Social 정보를 이용한 회원 가입
* Social 정보를 이용한 로그인, 로그아웃, 탈퇴
* 게시글 등록, 수정, 삭제, 조회 기능
* 게시글 덧글 등록 기능
* 게시글 댓글 등록, 수정, 삭제 기능 및 대댓글 등록, 수정, 삭제
* 게시글 관련 첨부파일 등록, 삭제
* 게시글 편집 기능

## Launch

## chap01
* 스프링 부트 설정

## chap02
* MariaDB 연결 및 테스트

## chap03
* Springboot DataSource

## chap04
* Mybatis

## chap05
* Validation

## chap06
* Thymeleaf

## chap07
* Filter

## chap08
* Spring Security

## chap09
* Spring OAuth2 Social Login

## chap10
* Post, Pagination, wysihtml5

## chap11
* Hierarchy Article Contents, Comments 

## Library Version
* gradle 라이브러리 버전 정보 정리
* lombok = '1.18.6'
* logback = '1.2.3'

## ERD

## SQL

```sql

CREATE DATABASE primavera DEFAULT CHARACTER SET utf8mb4;

CREATE USER 'primavera'@'localhost' IDENTIFIED BY 'primavera';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, ALTER ON primavera.* TO 'primavera'@'localhost';

CREATE TABLE USER (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  EMAIL VARCHAR(50) NOT NULL,
  PASSWORD VARCHAR(100) NOT NULL,
  NICKNAME VARCHAR(45) NOT NULL,
  STATUS CHAR(1) NOT NULL DEFAULT 'A',
  REG_DATE DATETIME NOT NULL,
  MOD_DATE DATETIME DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY EMAIL_UNIQUE (EMAIL)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE USER_CONNECTION (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  EMAIL VARCHAR(50) NOT NULL,
  PROVIDER TINYINT(11) NOT NULL,
  PROVIDER_ID BIGINT(20) NOT NULL,
  DISPLAY_NAME VARCHAR(45) DEFAULT NULL,
  PROFILE_URL VARCHAR(200) DEFAULT NULL,
  IMAGE_URL VARCHAR(200) DEFAULT NULL,
  ACCESS_TOKEN VARCHAR(200) NOT NULL,
  EXPIRE_TIME BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE USER_ROLE (
  USER_ID BIGINT(20) NOT NULL,
  ROLE_ID INT(11) NOT NULL,
  PRIMARY KEY (USER_ID, ROLE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE ROLE (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  TYPE TINYINT(3) NOT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE POST (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  WRITER_ID BIGINT(20) NOT NULL,
  SUBJECT VARCHAR(200) NOT NULL,
  CONTENTS TEXT NOT NULL,
  STATUS TINYINT(3) NOT NULL,
  REG_DT DATETIME DEFAULT NULL,
  MOD_DT DATETIME DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_WRITER_ID (WRITER_ID),
  CONSTRAINT FK_POST_WRITER_ID FOREIGN KEY (WRITER_ID) REFERENCES USER (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ARTICLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `P_ID` bigint(20) NOT NULL DEFAULT 0,
  `REFERENCE` bigint(20) NOT NULL,
  `STEP` int(11) NOT NULL,
  `LEVEL` int(11) NOT NULL,
  `WRITER_ID` bigint(20) NOT NULL,
  `SUBJECT` varchar(200) NOT NULL,
  `STATUS` tinyint(3) NOT NULL,
  `HIT` bigint(20) NOT NULL DEFAULT 0,
  `LIKE` bigint(20) NOT NULL DEFAULT 0,
  `DISLIKE` bigint(20) NOT NULL DEFAULT 0,
  `REG_DT` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `MOD_DT` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WRITER_ID_idx` (`WRITER_ID`),
  CONSTRAINT `FK_ARTICLE_WRITER_ID` FOREIGN KEY (`WRITER_ID`) REFERENCES `USER` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ARTICLE_CONTENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ARTICLE_ID` bigint(20) DEFAULT NULL,
  `CONTENTS` text DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ARTICLE_ID_idx` (`ARTICLE_ID`),
  CONSTRAINT `FK_ARTICLE_ID` FOREIGN KEY (`ARTICLE_ID`) REFERENCES `ARTICLE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO ROLE (TYPE) VALUES (1);
INSERT INTO ROLE (TYPE) VALUES (2);
INSERT INTO ROLE (TYPE) VALUES (3);

INSERT INTO `USER`(EMAIL, PASSWORD, NICKNAME, STATUS,REG_DATE) VALUES ('Genius Choi', '{bcrypt}$2a$10$7UEHLpn1r4gZY2qxiZFJ5.7wa3Hdz8IXgxUtFogy0Ac10fh7TG4V.', 'Genius', 1, NOW());
INSERT INTO `USER_CONNECTION`(EMAIL, PROVIDER, PROVIDER_ID, DISPLAY_NAME, PROFILE_URL, IMAGE_URL, ACCESS_TOKEN, EXPIRE_TIME) VALUES ('Genius Choi', 1, 123456789, 'Genius', 'PROFILE', 'IMAGE', '1234567890', -1);
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (1,1);
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (1,2);
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (1,3);

INSERT INTO `USER`(`EMAIL`,`PASSWORD`,`NICKNAME`,`STATUS`,`REG_DATE`) VALUES ('Son Heung-min', '{bcrypt}$2a$10$7UEHLpn1r4gZY2qxiZFJ5.7wa3Hdz8IXgxUtFogy0Ac10fh7TG4V.', 'Son', 1, NOW());
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (2,1);
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (2,2);
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (2,3);

INSERT INTO `USER`(`EMAIL`,`PASSWORD`,`NICKNAME`,`STATUS`,`REG_DATE`) VALUES ('Lionel Messi', '{bcrypt}$2a$10$7UEHLpn1r4gZY2qxiZFJ5.7wa3Hdz8IXgxUtFogy0Ac10fh7TG4V.', 'Messi', 1, NOW());
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (3,1);
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (3,2);

INSERT INTO `USER`(`EMAIL`,`PASSWORD`,`NICKNAME`,`STATUS`,`REG_DATE`) VALUES ('Cristiano Ronaldo', '{bcrypt}$2a$10$7UEHLpn1r4gZY2qxiZFJ5.7wa3Hdz8IXgxUtFogy0Ac10fh7TG4V.', 'Ronaldo', 1, NOW());
INSERT INTO `USER_ROLE`(`USER_ID`, ROLE_ID) VALUES (4,1);

```