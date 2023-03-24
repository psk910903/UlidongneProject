CREATE DATABASE ourTown;
USE ourTown;

DROP TABLE if EXISTS sns_member;
CREATE TABLE sns_member(
	sns_member_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	sns_member_name VARCHAR(255) NOT NULL,                       -- 닉네임(별명)
	sns_member_email VARCHAR(255) NOT NULL,                      -- 이메일(계정)
	sns_member_picture VARCHAR(255)  NULL,                       -- 프로필이미지 경로
	sns_member_role VARCHAR(255) DEFAULT 'ROLE_USER',            -- 권한
	sns_member_createddate DATE DEFAULT NOW()						    -- 만든 날짜
);


DROP TABLE if EXISTS member;
CREATE TABLE member(
   member_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- 회원 번호
   member_name VARCHAR(5) NOT NULL,                       -- 회원 이름
   member_phone CHAR(11) NOT NULL,                        -- 회원 전번
   member_birthday DATE NOT NULL,                         -- 회원 생년월일
   member_gender CHAR(1) NOT NULL,                        -- 회원 성별
   member_introduce VARCHAR(40) NOT NULL,                 -- 자기소개
   member_picture VARCHAR(255) NOT NULL,                  -- 프로필 이미지
   member_location VARCHAR(100) NOT NULL,                 -- 회원 위치
   member_interest_cate1 VARCHAR(10) NOT NULL,            -- 회원 관심사1
   member_interest_cate2 VARCHAR(10) NOT NULL,            -- 회원 관심사2
   member_interest_cate3 VARCHAR(10) NOT NULL,            -- 회원 관심사3
   member_interest_cate4 VARCHAR(10) NOT NULL,            -- 회원 관심사4
   member_interest_cate5 VARCHAR(10) NOT NULL,            -- 회원 관심사5
   interested_club TEXT NOT NULL,                         -- 찜한 모임 (배열)
   joined_club TEXT NOT NULL,                             -- 가입한 클럽 (배열)
   wait_club TEXT NOT NULL,                               -- 대기 상태 클럽 (배열)
   member_role VARCHAR(10) NOT NULL,                      -- 권한
   member_joindate DATE NOT NULL                          -- 가입일
);
-- 가입 모임은 text로 저장, 서버쪽에서는 숫자 배열을 문자열로 반환해서 보내준다.

DROP TABLE if EXISTS outmember;
CREATE TABLE outmember(
   outmember_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- 탈퇴 회원 번호
   outmember_name VARCHAR(5) NOT NULL,                        -- 탈퇴 회원 이름
   outmember_phone CHAR(11) NOT NULL,                         -- 탈퇴 회원 전번
   outmember_birthday DATE NOT NULL,                          -- 탈퇴 회원 생년월일
   outmember_gender CHAR(1) NOT NULL                          -- 탈퇴 회원 성별
);

DROP TABLE if EXISTS category;
CREATE TABLE category (
	category_idx INT PRIMARY KEY AUTO_INCREMENT,                -- 고유키
	category_main CHAR(255) NOT NULL,                           -- 대분류
	category_sub  CHAR(255) NOT NULL,                           -- 중분류
	category_image TEXT NOT NULL											-- 아이콘
);


DROP TABLE if EXISTS club;
CREATE TABLE club(
   club_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,       -- 모임 번호
   club_name VARCHAR(5) NOT NULL,                             -- 모임 이름
   club_location VARCHAR(30) NOT NULL,                        -- 기반 직역
   club_host BIGINT NOT NULL,                                 -- 모임장
   club_guest TEXT,                                           -- 모임 회원 (배열)
   club_wait_guest TEXT,                                      -- 대기 회원 (배열)
   club_category VARCHAR(10) NOT NULL,                        -- 모임 카테고리
   club_limit INT NOT NULL,                                   -- 제한 인원수
   club_introduct VARCHAR(50) NOT NULL,                       -- 모임 소개(목적)
   club_content VARCHAR(200) NOT NULL,                        -- 상세 정보
   chatting_idx INT NOT NULL,                                 -- 채팅방 번호
   club_profile_image TEXT NOT NULL,                          -- 클럽 대표 사진
   club_photos TEXT,                                          -- 클럽 사진 (배열)
   club_createdate DATE NOT NULL                              -- 모임 생성일
);


DROP TABLE if EXISTS `meeting`;
CREATE TABLE `meeting`(
   meeting_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,    -- 정모 번호
   meeting_club BIGINT NOT NULL,                              -- 클럽 번호
   meeting_title VARCHAR(50) NOT NULL,                        -- 정모 목적(이름)
   meeting_date DATE NOT NULL,                                -- 정모 날짜
   meeting_time TIME NOT NULL,                                -- 정모 시간
   meeting_end_time TIME NOT NULL,                            -- 끝난 시간
   meeting_location VARCHAR(30) NOT NULL,                     -- 정모 위치
   meeting_location_url VARCHAR(100),                         -- 정모 위치 url( url 기반 위치찾기 할거면)
   meeting_pay INT,                                           -- 참가비
   meeting_limit INT,                                         -- 최대 인원
   meeting_attend TEXT                                        -- 참가 회원 번호
);

DROP TABLE if EXISTS photo;
CREATE TABLE `photo`(
   photo_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,   -- 사진 번호
   member_idx BIGINT NOT NULL,                             -- 올린 회원 번호
   photo_url TEXT NOT NULL,                                -- 사진 url
   liet_member TEXT NOT NULL,                              -- 좋아요 누른 회원(배열)
   photo_regidate DATE NOT NULL                            -- 사진 등록 날짜
);


DROP TABLE if EXISTS photo_reply;
CREATE TABLE photo_reply (                            
	photo_reply_idx INT PRIMARY KEY AUTO_INCREMENT,    -- 고유키
	photo_reply_content CHAR(255) NOT NULL,            -- 내용
	photo_idx BIGINT NOT NULL,                         -- 사진 번호
	member_idx BIGINT NOT NULL,                        -- 작성자 번호
	photo_reply_datetime  DATETIME NOT NULL            -- 등록 시간
);


DROP TABLE if EXISTS chatting;
CREATE TABLE chatting(
   chatting_idx INT PRIMARY KEY AUTO_INCREMENT,		    -- 채팅방 번호
   chatting_name VARCHAR(20) NOT NULL,                 -- 방 이름
   chatting_createddate DATE NOT NULL,                 -- 방 생성일
   chatting_member TEXT NOT NULL                       -- 채팅 회원 번호(배열)
);

DROP TABLE if EXISTS chatting_log;
CREATE TABLE chatting_log(
   chatting_log_idx BIGINT PRIMARY KEY AUTO_INCREMENT,
   chatting_idx BIGINT NOT NULL,                       -- 채팅방 번호
   member_idx BIGINT NOT NULL,                         -- 채팅한 사람 번호
   chatting_content_type VARCHAR(10) NOT NULL,         -- 종류 (이미지, 동영상, 문자열 등), 사진 모아보기 같은 기능을 원하면
   chatting_content TEXT NOT NULL,                     -- 채팅 내용
   chatting_createddate TIMESTAMP NOT NULL             -- 채팅한 시간(datetime과 timestamp 두가지). 전자는 db기준으로 고정
                                                       -- 후자는 설정된 시간에 따라 바뀜
);