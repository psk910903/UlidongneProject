package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.FileResponse;
import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.Interface.MeetingService;
import com.study.UlidongneProject.service.Interface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AwsS3Service awsS3Service;
    private final Service3 service3;
    private final MemberRepository memberRepository;

    @Override
    public String join(MemberSaveRequestDto dto) {

        Optional<MemberEntity> optional = memberRepository.optionalFindByPhone(dto.getMemberPhone());
        if (optional.isPresent()) {
            return "-1";
        } else {
            String url;
            if (dto.getFile() != null) {
                url = awsS3Service.upload(dto.getFile());

                new ResponseEntity<>(FileResponse.builder().
                        uploaded(true).
                        url(url).
                        build(), HttpStatus.OK);

                dto.setMemberPicture(url);
            }

            LocalDate date = Service3.convertStringToLocalDate(dto.getMemberBirthdayStr());
            dto.setMemberBirthday(date);
            dto.setMemberJoinDate(LocalDate.now());

            String memberInterestCase4 = dto.getMemberInterestCase4();
            String memberInterestCase5 = dto.getMemberInterestCase5();
            if (memberInterestCase4.equals("")) {
                dto.setMemberInterestCase4(null);
            }
            if (memberInterestCase5.equals("")) {
                dto.setMemberInterestCase5(null);
            }
            MemberEntity entity = dto.toSaveEntity();
            System.out.println("entity = " + entity);

            memberRepository.save(entity);
            MemberEntity member = memberRepository.findByPhone(dto.getMemberPhone());
            return String.valueOf(member.getMemberIdx());
        }
    }

    @Override
    public void login() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void quit() {

    }

    @Override
    public void logout() {

    }
}
