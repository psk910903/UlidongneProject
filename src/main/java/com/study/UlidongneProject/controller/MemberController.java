package com.study.UlidongneProject.controller;

import com.study.UlidongneProject.dto.CategoryResponseDto;
import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.other.WebSockChatHandler;
import com.study.UlidongneProject.service.CategoryService;
import com.study.UlidongneProject.service.ClubServiceImpl;
import com.study.UlidongneProject.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;
    private final WebSockChatHandler webSockChatHandler;
    private final ClubServiceImpl clubService;
    private final CategoryService categoryService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/{param}") // 회원 정보 조회
    public String memberDetailPage(@PathVariable("param") Long memberIdx, Model model){
        MemberResponseDto memberResponseDto = memberService.findMemberByIdx(memberIdx);
        model.addAttribute("member", memberResponseDto);
        model.addAttribute("clubList", memberResponseDto.getClubList()); // member만 보냈더니 th:inline으로 못받음. 이유 모름
        return "clubContent/memberInfo";
    }

    @GetMapping("/member/{memberIdx}/information")
    public String changeMemberLocation(@RequestParam(required = false) String address,
                                       @PathVariable("memberIdx") Long memberIdx,
                                       Model model){
        MemberResponseDto memberResponseDto = memberService.findMemberByIdx(memberIdx);
        if(address != null) {
            List<String> addressList = Arrays.stream(address.split(" ")).toList();
            model.addAttribute("address", addressList);
            model.addAttribute("member", memberResponseDto);
        }else {
            List<String> addressList = Arrays.stream( memberResponseDto.getMemberLocation().split(" ") ).toList();
            model.addAttribute("address", addressList);
            model.addAttribute("member", memberResponseDto);
        }
        return "seeMore/myProfile";
    }


    @GetMapping("/member/{memberIdx}/more")
    public String seeMorePage(@PathVariable("memberIdx") Long memberIdx, Model model){
        MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
        model.addAttribute("member",memberDto);
        return "seeMore/seeMore";
    }

    @GetMapping("/member/{memberIdx}/club")
    public String memberJoinedClub(@PathVariable("memberIdx") Long memberIdx, Model model){
        List<ClubResponseDto> clubList = clubService.findMemberJoinedClub(memberIdx);

        model.addAttribute("club",clubList);
        model.addAttribute("size", clubList.size());
        return "seeMore/myJoinedClub";
    }

    @GetMapping("/member/{memberIdx}/category")
    public String memberCategory(@PathVariable("memberIdx") Long memberIdx, Model model){
        List<CategoryResponseDto> categoryDto = categoryService.findCategory();
        MemberResponseDto memberDto = memberService.findMemberByIdx(memberIdx);
        model.addAttribute("category", categoryDto);
        model.addAttribute("member", memberDto);
        return "seeMore/editMyCategory";
    }

    @GetMapping("/member/activity/{memberIdx}")
    public String myActivity(@PathVariable("memberIdx") Long memberIdx, Model model){
        MemberResponseDto dto =  memberService.findMemberByIdx(memberIdx);
        List<ClubResponseDto> list = clubService.findMemberJoinedClub(memberIdx);
        model.addAttribute("member", dto);
        model.addAttribute("club", list);
        model.addAttribute("count", list.size());
        model.addAttribute("category", memberService.findMyInterestCategory(memberIdx));
        model.addAttribute("recoClub", clubService.findMyRecommendClub(memberIdx));
        model.addAttribute("location", dto.getLocationLast());
        return "myActivity/myActivity";
    }

    @PutMapping( value = "/member/{memberIdx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public boolean updateMemberInfo(@PathVariable("memberIdx") Long idx, // 맴버 정보 수정
                                    @RequestParam(value = "memberPicture", required = false) MultipartFile picture,
                                    HttpServletRequest request,
                                    Model model){
        MemberResponseDto dto= memberService.modify(idx, request, picture);
        model.addAttribute("member", dto);
        return true;
    }

    @PatchMapping("/member/category/{memberIdx}")
    @ResponseBody
    public boolean updateMemberInterests(@PathVariable("memberIdx") Long idx,
                                         @RequestBody HashMap<String, String> data){
        return memberService.modifyCategory(idx, data);
    }

    @DeleteMapping("/member/{memberIdx}")
    @ResponseBody
    public boolean quitMember(@PathVariable("memberIdx") Long idx, HttpSession session){
        boolean handerResult = webSockChatHandler.sendQuitMessage(idx);
        boolean clubResult = clubService.memberQuit(idx);
        boolean memberResult = memberService.quit(idx, session);
        return memberResult && clubResult && handerResult;
    }

    // findAll
    @ResponseBody
    @GetMapping("/member")
    public List<MemberEntity> findAll() {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        List<MemberEntity>  memberList = memberRepository.findAll();
        return memberList;
    }

    // findById
    @ResponseBody
    @GetMapping("/member/idx/{memberIdx}")
    public MemberEntity findById(@PathVariable("memberIdx") Long memberIdx) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        MemberEntity memberEntity = memberRepository.findById(memberIdx).orElse(null);
        return memberEntity;
    }

    // findByPhone
    @ResponseBody
    @GetMapping("/member/phone/{memberPhone}")
    public MemberEntity findByPhone(@PathVariable("memberPhone") String memberPhone) {
        // 성공하면 memberEntity, 실패하면 null로 보내기
        MemberEntity memberEntity = memberRepository.findByPhone(memberPhone);
        return memberEntity;
    }


    @GetMapping("/onlylocation/search")
    public String onlyLocation(){
        return "/clubList/onlyLocation";
    }

    @ResponseBody
    @PatchMapping("/member/{memberIdx}/location")
    public boolean locationChange(@PathVariable("memberIdx") Long idx, HttpServletRequest request){
        memberService.modify(idx, request);
        return true;
    }
}
