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
INSERT INTO sns_member VALUE (null, '멍개', 'abc1234@naver.com', NULL, 'ROLE_USER', '2022-12-03');
INSERT INTO sns_member VALUE (null, '성개', 'abc1234@naver.com', NULL, 'ROLE_USER', '2022-12-03');


DROP TABLE if EXISTS member;
CREATE TABLE member(
   member_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- 회원 번호
   member_name VARCHAR(5) NOT NULL,                       -- 회원 이름(닉네임)
   member_phone CHAR(11) NOT NULL,                        -- 회원 전번
   member_birthday DATE NOT NULL,                         -- 회원 생년월일
   member_gender CHAR(1) NOT NULL,                        -- 회원 성별
   member_introduce VARCHAR(40) NOT NULL,                 -- 자기소개
   member_picture VARCHAR(255) NULL,                      -- 프로필 이미지
   member_location VARCHAR(100) NOT NULL,                 -- 회원 위치
   member_interest_cate1 VARCHAR(10) NOT NULL,            -- 회원 관심사1
   member_interest_cate2 VARCHAR(10) NOT NULL,            -- 회원 관심사2
   member_interest_cate3 VARCHAR(10) not NULL,            -- 회원 관심사3
   member_interest_cate4 VARCHAR(10) NULL,                -- 회원 관심사4
   member_interest_cate5 VARCHAR(10) NULL,                -- 회원 관심사5
   interested_club TEXT NOT NULL,                         -- 찜한 모임 (배열)
   joined_club TEXT NOT NULL,                             -- 가입한 클럽 (배열)
   wait_club TEXT NOT NULL,                               -- 대기 상태 클럽 (배열)
   member_role VARCHAR(10) NOT NULL,                      -- 권한
   member_joindate DATE NOT NULL                          -- 가입일
);
-- 가입 모임은 text로 저장, 서버쪽에서는 숫자 배열을 문자열로 반환해서 보내준다.
INSERT INTO member VALUE (NULL, '김수한', '01012345678', '2002-04-01', '남', '사람과의 만남을 좋아하는 20대입니다.', NULL,
 '서울특별시 동대문구 회기1동', '독서', '영화 감상', '스포츠', '봉사활동', NULL, '[2, 3]', '[1]',
  '[2,4]', 'ROLE_USER', '2023-03-24' );
INSERT INTO member VALUE (NULL, '무거북이', '01022345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 30대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '[3, 4]', '[1,2]',
  '[3]', 'ROLE_USER', '2023-02-10' );
INSERT INTO member VALUE (NULL, '와두루미', '01032345678', '1990-11-13', '여', '사람과의 만남을 좋아하는 40대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '[4]', '[3,4]',
  '[2,4]', 'ROLE_USER', '2023-02-10' );
INSERT INTO member VALUE (NULL, '삼천갑자', '01042345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 50대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '[]', '[1,4]',
  '[2,3]', 'ROLE_USER', '2023-02-10' );


DROP TABLE if EXISTS outmember;
CREATE TABLE outmember(
   outmember_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- 탈퇴 회원 번호
   outmember_name VARCHAR(5) NOT NULL,                        -- 탈퇴 회원 이름
   outmember_phone CHAR(11) NOT NULL,                         -- 탈퇴 회원 전번
   outmember_birthday DATE NOT NULL,                          -- 탈퇴 회원 생년월일
   outmember_gender CHAR(1) NOT NULL                          -- 탈퇴 회원 성별
);
INSERT INTO outmember VALUE(null, '동방삭', '01052345678', '1924-02-17', '남');



DROP TABLE if EXISTS category;
CREATE TABLE category (
	category_idx INT PRIMARY KEY AUTO_INCREMENT,                -- 고유키
	category_main CHAR(255) NOT NULL,                           -- 대분류
	category_sub  CHAR(255) NOT NULL,                           -- 중분류
	category_image TEXT NOT NULL											-- 아이콘
);
INSERT INTO category VALUE(null, '자기계발', '독서', 'https://img.icons8.com/color/256/open-book.png');
INSERT INTO category VALUE(null, '휴식/ 여가', '음악 감상', 'https://img.icons8.com/external-vitaliy-gorbachev-flat-vitaly-gorbachev/256/external-turntable-radio-vitaliy-gorbachev-flat-vitaly-gorbachev.png');
INSERT INTO category VALUE(NULL, '휴식/ 여가', '영화 감상', 'https://img.icons8.com/ios/256/film-reel.png');
INSERT INTO category VALUE(null, '휴식/ 여가', '음악 연주', 'https://img.icons8.com/ios/256/film-reel.png');
INSERT INTO category VALUE(NULL, '애완동물', '개 훈련', 'https://img.icons8.com/external-flaticons-lineal-color-flat-icons/256/external-dog-training-dog-training-flaticons-lineal-color-flat-icons-3.png');
INSERT INTO category VALUE(null, '엔터테이먼트', '게임', 'https://img.icons8.com/ios-filled/256/ps-controller.png');
INSERT INTO category VALUE(null, '운동/ 헬스', '스포츠', 'ttps://img.icons8.com/color/256/sports.png');
INSERT INTO category VALUE(null, '운동/ 헬스', '등산', 'ttps://img.icons8.com/color/256/sports.png');
INSERT INTO category VALUE(null, '봉사', '봉사활동', 'https://img.icons8.com/doodle/256/volunteering.png');
INSERT INTO category VALUE(null, '외식/요리', '식도락', 'https://img.icons8.com/external-those-icons-lineal-those-icons/256/external-Fork-And-Knife-hotel-those-icons-lineal-those-icons.png');


DROP TABLE if EXISTS club;
CREATE TABLE club(
   club_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,       -- 모임 번호
   club_name VARCHAR(20) NOT NULL,                            -- 모임 이름
   club_location VARCHAR(30) NOT NULL,                        -- 기반 직역
   club_host BIGINT NOT NULL,                                 -- 모임장
   club_guest TEXT,                                           -- 모임 회원 (배열)
   club_wait_guest TEXT,                                      -- 대기 회원 (배열)
   club_category VARCHAR(10) NOT NULL,                        -- 모임 카테고리
   club_limit INT NOT NULL,                                   -- 제한 인원수
   club_introduct VARCHAR(50) NOT NULL,                       -- 모임 소개(목적)
   club_content VARCHAR(200) NOT NULL,                        -- 상세 정보
   chatting_idx INT NOT NULL,                                 -- 채팅방 번호  -- 클럽 번호와 동일하게
   club_profile_image TEXT NOT NULL,                          -- 클럽 대표 사진
   club_photos TEXT,                                          -- 클럽 사진 (배열)
   club_createdate DATE NOT NULL                              -- 모임 생성일
);
INSERT INTO club VALUE(null, '여러사랑 산악회', '휘경동', 1, '[1,2,4]', '[]', '등산', 10, '북한산 주로 등산하는 산악회입니다',
'한사랑산악회가 아닙니다. 잘못알고 가입한 사람은 나가주세요.', 1, 'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
 , '[1,2]', '2022-03-04' );
 INSERT INTO club VALUE(null, '한사랑산악회', '회기1동', 2, '[2]', '[4]', '명상', 10, '북한산에서 명상하는 모임입니다.',
'명상합니다.', 2, 'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
 , '[3,4]', '2023-01-24' );
 INSERT INTO club VALUE(null, '이문동 게임모임', '휘경동', 3, '[3]', '[1]', '등산', 10, '북한산 주로 등산하는 산악회입니다',
'산악회가 아닙니다. 잘못알고 가입한 사람은 나가주세요.', 3, 'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
 , '[5,6]', '2021-02-07' );
  INSERT INTO club VALUE(null, '인어선장 해적단', '이문동', 4, '[3,4]', '[1]', '식도락', 10, '먹으러 다닙니다.',
'먹습니다. 많이.', 4, 'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
 , '[]', '2022-03-02' );


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
   meeting_attend TEXT                                        -- 참가 회원 번호(배열)
);
INSERT INTO meeting VALUE (NULL, 1, '북한산 등반', '2022-04-07', '14:00:00', '20:00:00', '북한산 3번 등산로 입구', NULL, 12000, 10, '[1,2,3]');

DROP TABLE if EXISTS photo;
CREATE TABLE `photo`(
   photo_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,   -- 사진 번호
   member_idx BIGINT NOT NULL,                             -- 올린 회원 번호
   photo_url TEXT NOT NULL,                                -- 사진 url
   liet_member TEXT NOT NULL,                              -- 좋아요 누른 회원(배열)
   photo_regidate DATE NOT NULL                            -- 사진 등록 날짜
);
INSERT INTO photo VALUE(null, 1, 'https://upload.wikimedia.org/wikipedia/commons/6/60/Insoo_peak.jpg', '[1]', '2022-04-18');
INSERT INTO photo VALUE(null, 1, 'http://t1.gstatic.com/licensed-image?q=tbn:ANd9GcSJwiFSb9PZFAJEcxXQ9vJ7Px7GJE6acvhcgm9p9gRRtlNY0BWWtoitMUD4-vbqykuI', '[1]', '2022-04-18');
INSERT INTO photo VALUE(null, 4, 'https://upload.wikimedia.org/wikipedia/commons/6/60/Insoo_peak.jpg', '[2]', '2022-04-18');
INSERT INTO photo VALUE(null, 2, 'http://t1.gstatic.com/licensed-image?q=tbn:ANd9GcSJwiFSb9PZFAJEcxXQ9vJ7Px7GJE6acvhcgm9p9gRRtlNY0BWWtoitMUD4-vbqykuI', '[2,4]', '2022-04-18');
INSERT INTO photo VALUE(null, 3, 'http://t1.gstatic.com/licensed-image?q=tbn:ANd9GcSJwiFSb9PZFAJEcxXQ9vJ7Px7GJE6acvhcgm9p9gRRtlNY0BWWtoitMUD4-vbqykuI', '[]', '2022-04-18');
INSERT INTO photo VALUE(null, 3, 'https://upload.wikimedia.org/wikipedia/commons/6/60/Insoo_peak.jpg', '[3]', '2022-04-18');

DROP TABLE if EXISTS photo_reply;
CREATE TABLE photo_reply (                            
	photo_reply_idx INT PRIMARY KEY AUTO_INCREMENT,    -- 고유키
	photo_reply_content CHAR(255) NOT NULL,            -- 내용
	photo_idx BIGINT NOT NULL,                         -- 사진 번호
	member_idx BIGINT NOT NULL,                        -- 작성자 번호
	photo_reply_datetime  DATETIME NOT NULL            -- 등록 시간
);
INSERT INTO photo_reply VALUE(NULL, '따봉', '1', '1', '2022-04-18');
INSERT INTO photo_reply VALUE(NULL, '따따봉', '1', '2', '2022-04-19');
INSERT INTO photo_reply VALUE(NULL, '따봉2', '3', '3', '2022-04-17');
INSERT INTO photo_reply VALUE(NULL, '따따봉2', '3', '3', '2022-04-20');


DROP TABLE if EXISTS chatting;
CREATE TABLE chatting(
   chatting_idx INT PRIMARY KEY AUTO_INCREMENT,		    -- 채팅방 번호
   chatting_name VARCHAR(20) NOT NULL,                 -- 방 이름
   chatting_createddate DATE NOT NULL,                 -- 방 생성일
   chatting_member TEXT NOT NULL                       -- 채팅 회원 번호(배열)
);
INSERT INTO chatting VALUE(1, '여러사랑 산악회 채팅방', '2022-03-04', '[1,2,4]');

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
INSERT INTO chatting_log VALUE(null, 1, 1, 'text', '어디로 갈래요?', '2022-03-04 14:11:09');
INSERT INTO chatting_log VALUE(null, 2, 1, 'text', '종로로 갈까요?', '2022-03-04 14:11:30');
INSERT INTO chatting_log VALUE(null, 4, 1, 'text', '청량리로 갈까요?', '2022-03-04 14:12:09');