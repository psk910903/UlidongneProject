CREATE DATABASE ourTown;
USE ourTown;

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
   member_interest_cate3 VARCHAR(10) NOT NULL,            -- 회원 관심사4
   member_interest_cate3 VARCHAR(10) NOT NULL,            -- 회원 관심사5
   interested_club TEXT NOT NULL,                         -- 찜한 모임 (배열)
   joined_club TEXT NOT NULL,                             -- 가입한 클럽 (배열)
   member_role VARCHAR(10) NOT NULL,                      -- 권한
   member_joindate DATE NOT NULL                          -- 가입일
);
-- 가입 모임은 text로 저장, 서버쪽에서는 숫자 배열을 문자열로 반환해서 보내준다.

CREATE TABLE outmember(
   outmember_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- 탈퇴 회원 번호
   outmember_name VARCHAR(5) NOT NULL,                        -- 탈퇴 회원 이름
   outmember_phone CHAR(11),                                  -- 탈퇴 회원 전번
   outmember_birthday DATE NOT NULL,                          -- 탈퇴 회원 생년월일
   outmember_gender CHAR(1) NOT NULL,                         -- 탈퇴 회원 성별
);


-- 카테고리 테이블 (논의)
CREATE TABLE category (
 category_no INT PRIMARY KEY AUTO_INCREMENT,                -- 고유키
 main_category CHAR(255) NOT NULL,                          -- 대분류
 middle_category  CHAR(255) NOT NULL                        -- 중분류
);


CREATE TABLE club(
   club_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,       -- 모임 번호
   club_name VARCHAR(5) NOT NULL,                             -- 모임 이름
   club_location VARCHAR(30) NOT NULL,                        -- 기반 직역
   club_host INT NOT NULL,                                    -- 모임장
   club_guest TEXT,                                           -- 모임 회원 (배열)
   club_category VARCHAR(10) NOT NULL,                        -- 모임 카테고리
   limit_num INT NOT NULL,                                    -- 제한 인원수
   club_introduct varchr(50) NOT NULL,                        -- 모임 소개(목적)
   club_content VARCHAR(200) NOT NULL,                        -- 상세 정보
   chattingRoom_idx INT NOT NULL,                             -- 채팅방 번호
   club_photos TEXT,                                          -- 클럽 사진
   club_createdate DATE NOT NULL                              -- 모임 생성일
);


-- 모임 가입 DB (논의)
DROP TABLE if EXISTS room_join;
CREATE TABLE room_join (
   room_join_no BIGINT PRIMARY KEY AUTO_INCREMENT,      -- 고유키
   room_no BIGINT NOT NULL,                             -- 모임 FK
   member_no BIGINT NOT NULL,                           -- 회원 FK
	room_join_date DATETIME,                             -- 요청한 날짜
	room_join_state CHAR(2) NOT NULL                     -- 요청 상태(대기, 가입, 거절)
);


CREATE TABLE club_meeting(
   meeting_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,    -- 정모 번호
   meeting_club BIGINT NOT NULL,                              -- 클럽 번호
   meeting_title varchr(50) NOT NULL,                         -- 정모 목적(이름)
   meeting_date DATE NOT NULL,                                -- 정모 날짜
   meeting_time TIME NOT NULL,                                -- 정모 시간
   meeting_end_tiem TIME NOT NULL,                            -- 끝난 시간
   meeting_location VARCHAR(30) NOT NULL,                     -- 정모 위치
   meeting_location_url VARCHAR(100),                         -- 정모 위치 url( url 기반 위치찾기 할거면)
   meeting_pay INT,                                           -- 참가비
   meeting_limit INT,                                         -- 최대 인원
   meeting_attend TEXT                                        -- 참가 회원 번호
);


CREATE TABLE photo(
   photo BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,   -- 사진 번호
   member_idx BIGINT NOT NULL,                         -- 올린 회원 번호
   photo TEXT NOT NULL,                                -- 사진 url
   liet_member TEXT NOT NULL,                          -- 좋아요 누른 회원(배열)
   photo_regidate DATE NOT NULL                        -- 사진 등록 날짜
);


-- 사진첩 덧글달기(논의)
CREATE TABLE photo_reply ( -- meeting
 photo_reply_idx INT PRIMARY KEY AUTO_INCREMENT,    -- 고유키
 reply_content    CHAR(255) NOT NULL,               -- 내용
 photo_idx     CHAR(255) NOT NULL,                 -- 사진 번호
 member_idx  CHAR(255) NOT NULL,                   -- 작성자 번호
 reply_date_time  DATETIME NOT NULL                -- 등록 시간
);



CREATE TABLE chatting_room(
   room_idx INT NOT NUL PRIMARY KEY, AUTO_INCREMENT,   -- 채팅방 번호
   room_name VARCHAR(20) NOT NULL,                     -- 방 이름
   room_createddate DATE NOT NULL,                     -- 방 생성일
   chating_member TEXT NOT NULL                        -- 채팅 회원 번호(배열)
);


CREATE TABLE chatting_log(
   log_idx bigint PRIMARY KEY, AUTO_INCREMENT,
   room_idx BIGINT NOT NULL,                           -- 채팅방 번호
   member_idx BIGINT NOT NULL,                         -- 채팅한 사람 번호
   content_type VARCHAR(10) NOT NULL,                  -- 종류 (이미지, 동영상, 문자열 등), 사진 모아보기 같은 기능을 원하면
   content TEXT NOT NULL,                              -- 채팅 내용
   created_date TIMESTAMP NOT NULL                     -- 채팅한 시간(datetime과 timestamp 두가지). 전자는 db기준으로 고정
                                                       -- 후자는 설정된 시간에 따라 바뀜
);