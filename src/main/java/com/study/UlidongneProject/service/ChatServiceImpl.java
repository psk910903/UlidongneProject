package com.study.UlidongneProject.service;

import com.study.UlidongneProject.dto.ChattingResponseDto;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;
import com.study.UlidongneProject.entity.ChattingEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ChattingRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.Interface.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChattingRepository chattingRepository;

    private final MemberRepository memberRepository;

    @Override
    public List<ChattingResponseDto> findByClubIdx(Long clubIdx) {
        List<ChattingEntity> list = chattingRepository.findByClubIdx(clubIdx);
        return list.stream().map(ChattingResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean save(ChattingSaveRequestDto dto) {

        ChattingEntity entity = dto.toSaveEntity();
        try {
            chattingRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<String> findMemberPictureByClubIdx(Long clubIdx) {
        List<ChattingEntity> list = chattingRepository.findByClubIdx(clubIdx);
        List<String> memberPictureList = new ArrayList<>();
        for (ChattingEntity entity: list){
            Optional<MemberEntity> member = memberRepository.findById(entity.getMemberIdx());
            if (member.isPresent()){
                memberPictureList.add(member.get().getMemberPicture());
            }else{
                memberPictureList.add(null);
            }
        }

        return memberPictureList;
    }

    @Override
    public List<String> findMemberNameByClubIdx(Long clubIdx) {
        List<ChattingEntity> list = chattingRepository.findByClubIdx(clubIdx);
        List<String> memberNameList = new ArrayList<>();
        for (ChattingEntity entity: list){
            Optional<MemberEntity> member = memberRepository.findById(entity.getMemberIdx());
            if (member.isPresent()){
                memberNameList.add(member.get().getMemberName());
            }else{
                memberNameList.add(null);
            }
        }
        return memberNameList;
    }

}
