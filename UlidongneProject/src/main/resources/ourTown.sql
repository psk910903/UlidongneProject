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
   member_interest_case1 VARCHAR(200) NOT NULL,            -- 회원 관심사1
   member_interest_case2 VARCHAR(200) NOT NULL,            -- 회원 관심사2
   member_interest_case3 VARCHAR(200) not NULL,            -- 회원 관심사3
   member_interest_case4 VARCHAR(200) NULL,                -- 회원 관심사4
   member_interest_case5 VARCHAR(200) NULL,                -- 회원 관심사5
   joined_club TEXT NOT NULL,                             -- 가입한 클럽 (배열)
   wait_club TEXT NOT NULL,                               -- 대기 상태 클럽 (배열)
   member_role VARCHAR(10) NOT NULL,                      -- 권한
   member_joindate DATE NOT NULL                          -- 가입일
);
-- 가입 모임은 text로 저장, 서버쪽에서는 숫자 배열을 문자열로 반환해서 보내준다.
INSERT INTO member VALUE (NULL, '김수한', '01012345678', '2002-04-01', '남', '사람과의 만남을 좋아하는 20대입니다.', NULL,
 '서울특별시 동대문구 회기1동', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%9D%8C%EC%95%85-48+(1).png',
  'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%96%B8%EC%96%B4-50.png',
  'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%9E%90%EC%A0%84%EA%B1%B0-50.png',
  'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%9A%94%EB%A6%AC%EC%82%AC-%EB%AA%A8%EC%9E%90-50.png',
   NULL, '{1}',
  '{2,4}', 'ROLE_USER', '2023-03-24' );
INSERT INTO member VALUE (NULL, '무거북이', '01022345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 30대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL,  '{1,2}',
  '{3}', 'ROLE_USER', '2023-02-10' );
INSERT INTO member VALUE (NULL, '와두루미', '01032345678', '1990-11-13', '여', '사람과의 만남을 좋아하는 40대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '{3,4}',
  '{2,4}', 'ROLE_USER', '2023-02-10' );
INSERT INTO member VALUE (NULL, '삼천갑자', '01042345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 50대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '{1,4}',
  '{2,3}', 'ROLE_USER', '2023-02-10' );

INSERT INTO member VALUE (NULL, '삼천갑자', '01042345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 50대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '{1,4}',
  '{2,3}', 'ROLE_USER', '2023-02-10' );

INSERT INTO member VALUE (NULL, '삼천갑자', '01042345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 50대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '{1,4}',
  '{2,3}', 'ROLE_USER', '2023-02-10' );
INSERT INTO member VALUE (NULL, '삼천갑자', '01042345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 50대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '{1,4}',
  '{2,3}', 'ROLE_USER', '2023-02-10' );
INSERT INTO member VALUE (NULL, '삼천갑자', '01042345678', '1990-11-13', '여', '사람과의 단절을 좋아하는 50대입니다.', NULL,
 '서울특별시 동대문구 이문동', '게임', '음악 연주', '헬스', '개 훈련', NULL, '{1,4}',
  '{2,3}', 'ROLE_USER', '2023-02-10' );

SELECT * FROM member;

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
	category_image TEXT NOT NULL											-- 아이콘
);
INSERT INTO category VALUE(null, '음악', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%9D%8C%EC%95%85-48+(1).png');
INSERT INTO category VALUE(null, '외국어', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%96%B8%EC%96%B4-50.png');
INSERT INTO category VALUE(NULL, '운동', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%9E%90%EC%A0%84%EA%B1%B0-50.png');
INSERT INTO category VALUE(null, '자유주제', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%ED%95%98%ED%8A%B8-50.png');
INSERT INTO category VALUE(null, '요리', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%9A%94%EB%A6%AC%EC%82%AC-%EB%AA%A8%EC%9E%90-50.png');
INSERT INTO category VALUE(null, '직무', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%84%9C%EB%A5%98-%EA%B0%80%EB%B0%A9-64.png');
INSERT INTO category VALUE(null, '인문학', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EB%8F%84%EC%84%9C-50.png');
INSERT INTO category VALUE(null, '친목', 'https://psk-s3-bucket.s3.ap-northeast-2.amazonaws.com/icons8-%EC%B9%9C%EA%B5%AC-50.png');

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
   club_introduce VARCHAR(50) NOT NULL,                       -- 모임 소개(목적)
   club_content VARCHAR(200) NOT NULL,                        -- 상세 정보
   club_profile_image TEXT NOT NULL,                          -- 클럽 대표 사진
   club_create_date DATE NOT NULL                             -- 모임 생성일
);
INSERT INTO club VALUE(null, '여러사랑 산악회', '서울특별시 동대문구 휘경동', 1, '{1,2,4}', '{}', '운동', 10, '북한산 주로 등산하는 산악회입니다',
'한사랑산악회가 아닙니다. 잘못알고 가입한 사람은 나가주세요.',  'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
  ,'2022-03-04' );
 INSERT INTO club VALUE(null, '한사랑산악회', '서울특별시 동대문구 회기1동', 2, '{2}', '{4}', '자유주제', 10, '북한산에서 명상하는 모임입니다.',
'명상합니다.',  'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
, '2023-01-24' );
 INSERT INTO club VALUE(null, '이문동 게임모임', '서울특별시 동대문구 휘경동', 3, '{3}', '{1}', '운동', 10, '북한산 주로 등산하는 산악회입니다',
'산악회가 아닙니다. 잘못알고 가입한 사람은 나가주세요.',  'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
 , '2021-02-07' );
  INSERT INTO club VALUE(null, '인어선장 해적단', '서울특별시 동대문구 이문동', 4, '{3,4}', '{1}', '요리', 10, '먹으러 다닙니다.',
'먹습니다. 많이.',  'https://www.knps.or.kr/upload/contest/21/20221108082032573.jpg'
  ,'2022-03-02' );

SELECT * FROM club;



DROP TABLE if EXISTS `meeting`;
CREATE TABLE `meeting`(
   meeting_idx BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,    -- 정모 번호
   meeting_club BIGINT NOT NULL,                              -- 클럽 번호
   meeting_title VARCHAR(50) NOT NULL,                        -- 정모 목적(이름)
   meeting_date DATE NOT NULL,                                -- 정모 날짜
   meeting_time VARCHAR(30) NOT NULL,                         -- 정모 시간
   meeting_location VARCHAR(30) NOT NULL,                     -- 정모 위치
   meeting_location_url VARCHAR(100),                         -- 정모 위치 url( url 기반 위치찾기 할거면)
   meeting_pay VARCHAR(30),                                   -- 참가비
   meeting_limit INT,                                         -- 최대 인원
   meeting_attend TEXT                                        -- 참가 회원 번호(배열)
);
INSERT INTO meeting VALUE (NULL, 1, '북한산 등반', '2023-03-31', '오후 2:00', '북한산 3번 등산로 입구', NULL, 12000, 10, '{1,2,3,4,5,6,7,8}');
INSERT INTO meeting VALUE (NULL, 1, '북한산 등반', '2023-03-30', '오후 2:00', '북한산 3번 등산로 입구', NULL, 12000, 10, '{1,2,3}');
INSERT INTO meeting VALUE (NULL, 1, 'test', '2023-03-31', '오후 12:00', '북한산 3번 등산로 입구', NULL, 12000, 10, '{2,3,1}');
INSERT INTO meeting VALUE (NULL, 2, 'test123', '2023-03-30', '오후 5:00', '북한산 3번 등산로 입구', NULL, 12000, 10, '{2,3}');
INSERT INTO meeting VALUE (NULL, 1, 'test12', '2023-04-20', '오후 5:00', '북한산 3번 등산로 입구', NULL, 12000, 10, '{1,2,3}');
SELECT * FROM meeting;


DROP TABLE if EXISTS chatting;
CREATE TABLE chatting(
   chatting_idx BIGINT PRIMARY KEY AUTO_INCREMENT,     -- 고유키
   club_idx BIGINT NOT NULL,                           -- 모임방 번호
   member_idx BIGINT NOT NULL,                         -- 채팅한 사람 번호
   chatting_type VARCHAR(10) NOT NULL,                 -- 종류 (이미지, 동영상, 문자열 등), 사진 모아보기 같은 기능을 구현하려면
   chatting_content TEXT NOT NULL,                     -- 채팅 내용
   chatting_write_time DATETIME NOT NULL               -- 채팅한 시간(datetime과 timestamp 두가지). 전자는 db기준으로 고정
                                                       -- 후자는 설정된 시간에 따라 바뀜
);
INSERT INTO chatting VALUE(NULL, 1, 1, 'text', '어디로 갈래요?', '2022-03-04 14:11:09');
INSERT INTO chatting VALUE(NULL, 1, 2, 'text', '종로로 갈까요?', '2022-03-04 14:11:30');
INSERT INTO chatting VALUE(NULL, 2, 3, 'text', '청량리로 갈까요?', '2022-03-04 14:12:09');


DROP TABLE if EXISTS notice;
CREATE TABLE notice(
   notice_idx BIGINT PRIMARY KEY AUTO_INCREMENT,       -- 공지사항 번호
   notice_title VARCHAR(30) NOT NULL,                  -- 공지사항 제목
   notice_content TEXT NOT NULL,                       -- 내용
   notice_createddate DATE NOT NULL                    -- 등록일
);
INSERT INTO notice VALUE (NULL, '공지사항1', '공지사항입니다.', '2023-01-01');

CREATE TABLE IF NOT EXISTS `new_zip` (
`ZIP_NO` VARCHAR(5) NULL COMMENT '우편번호',
`SIDO` VARCHAR(20) NULL COMMENT '시도',
`SIGUNGU` VARCHAR(20) NULL COMMENT '시군구',
`EUPMYUN` VARCHAR(20) NULL COMMENT '읍면',
`DONG_NM` VARCHAR(20) NULL COMMENT '법정동명',
`RI` VARCHAR(20) NULL COMMENT '리명',
`H_DONG_NM` VARCHAR(40) NULL COMMENT '행정동명'
)COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE INDEX RI ON new_zip (RI);
CREATE INDEX DONG_NM ON new_zip (DONG_NM);

ALTER TABLE new_zip DROP COLUMN H_DONG_NM;
ALTER TABLE new_zip DROP COLUMN EUPMYUN;