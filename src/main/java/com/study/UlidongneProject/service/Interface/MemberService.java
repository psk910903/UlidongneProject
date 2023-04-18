package com.study.UlidongneProject.service.Interface;

import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.dto.MemberSaveRequestDto;
import com.study.UlidongneProject.entity.MemberEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface MemberService extends UserDetailsService {

    String join(MemberSaveRequestDto dto);

    MemberResponseDto modify(Long idx , HttpServletRequest request, MultipartFile memberPicture);

    boolean modifyCategory(Long memberIdx, HashMap<String, String> data);

    boolean quit(Long idx, HttpSession session);

}
