package com.study.UlidongneProject.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.study.UlidongneProject.entity.*;
import com.study.UlidongneProject.entity.repository.CategoryRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class Service3 implements UserDetailsService{ //, OAuth2UserService<OAuth2UserRequest, OAuth2User>

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    //사용자 아이디를 통해, 사용자 정보와 권한을 스프링시큐리티에 전달한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //ADMIN 권한/역할을 넣는다.
        authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        //“1234”문자열을 Bcrypt 사이트(bcrypt-generator.com)에서 암호 생성하여 넣는다.
        return new User("hong", "$2a$12$CLFNXQConBP9WhVNqpWYY.5RmFID66xYzDI8yOFRf.RC/Qac41QjG", authorities);
    }

    @Transactional(readOnly = true)
    public MemberEntity findById(String phone) {
        MemberEntity memberEntity = new MemberEntity();
        try {
            memberEntity = memberRepository.findByPhone(phone);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("오류발생");
        }
        return memberEntity;
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> categoryFindAll() {
        List<CategoryEntity> categoryList = new ArrayList<>();
        try {
            categoryList = categoryRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("오류발생");
        }
        return categoryList;
    }

    public static LocalDate convertStringToLocalDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        return localDate;
    }

}