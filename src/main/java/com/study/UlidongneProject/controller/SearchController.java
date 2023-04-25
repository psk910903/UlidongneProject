package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.ZipcodeDto;
import com.study.UlidongneProject.entity.ClubEntity;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.ClubRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.other.PublicMethod;
import com.study.UlidongneProject.service.ClubServiceImpl;
import com.study.UlidongneProject.service.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final MemberRepository memberRepository;
    private final SearchServiceImpl searchService;
    private final ClubRepository clubRepository;
    private final ClubServiceImpl clubService;

    @GetMapping("/location/member/keyword/{keyword}/{what}")
    @ResponseBody

    public List<ZipcodeDto> locationSearch(@PathVariable("keyword") String keyword) { // 맴버 위치 검색
        return searchService.findLocation(keyword);
    }

    @GetMapping("/location/club/keyword/{keyword}/{what}")
    @ResponseBody
    public List<ZipcodeDto> clubLocationSearch(@PathVariable("keyword") String keyword) { // 클럽위치 검색
        return searchService.findLocation(keyword);
    }

    // 찾기
    @ResponseBody
    @GetMapping("/member/location/{keyword}/{page}")
    public List<MemberEntity> search(@PathVariable("keyword") String keyword, @PathVariable("page") int page) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        List<MemberEntity> memberList = memberRepository.findAll();
        return memberList;
    }

    // 키워드로 찾기 page 로드
    @GetMapping("/search/keyword/{keyword}/{location}")
    public String searchClubByKeyword(@PathVariable("keyword") String keyword, @PathVariable("location") String location, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        return "clubList/searchKeyword";
    }

    // 키워드로 찾기 실행
    @ResponseBody
    @GetMapping("/club/keyword/{keyword}/{location}/{page}")
    public Page<ClubResponseDto> searchClubByKeyword(@PathVariable("keyword") String keyword,
                                                     @PathVariable("location") String location,
                                                     @PathVariable("page") int page) {
        Page<ClubResponseDto> clubList = searchService.findByKeyword(keyword, location, page);
        return clubList;
    }

    // 카테고리로 찾기 page 로드
    @GetMapping("/search/category/{category}")
    public String searchClubByCategory(@PathVariable("category") String category, Model model) {
        List<ClubEntity> categoryList = clubRepository.findByCategory(category);
        List<ClubResponseDto> clubDtoList = clubService.settingClubLocation(categoryList);

        model.addAttribute("clubDtoList", clubDtoList);
        model.addAttribute("listSize", clubDtoList.size());
        model.addAttribute("category", category);
        return "clubList/searchCategory";
    }

    // 카테고리로 찾기 실행
    @ResponseBody
    @GetMapping("/club/category/{category}/keyword/{keyword}/{page}")
    public Page<ClubResponseDto> searchClubByCategory(@PathVariable("category") String category, @PathVariable("keyword") String keyword, @PathVariable("page") int page)  {
        Page<ClubResponseDto> clubList = searchService.findByCategory(category, keyword, page);
        return clubList;
    }
}
