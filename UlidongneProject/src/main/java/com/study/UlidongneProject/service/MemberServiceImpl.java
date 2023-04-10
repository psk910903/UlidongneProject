package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.FileResponse;
import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.Interface.MeetingService;
import com.study.UlidongneProject.service.Interface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AwsS3Service awsS3Service;
    private Service3 service3;
    private MemberRepository memberRepository;

    @Override
    public void join(MemberSaveRequestDto dto) {
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

        memberRepository.save(dto.toSaveEntity());
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
