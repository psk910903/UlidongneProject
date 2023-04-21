package com.study.UlidongneProject.service;

import com.study.UlidongneProject.entity.*;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SecurityService implements UserDetailsService{ //, OAuth2UserService<OAuth2UserRequest, OAuth2User>

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final MemberRepository memberRepository;

    //사용자 아이디를 통해, 사용자 정보와 권한을 스프링시큐리티에 전달한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity memberEntity = findByUserPhone(username);

        String memberRole = memberEntity.getMemberRole();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ( memberRole.contains("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        String encode = passwordEncoder.encode(memberEntity.getMemberName());
        return new User( memberEntity.getMemberPhone(), encode , authorities);
    }

    @Transactional(readOnly = true)
    public MemberEntity findByUserPhone(String phone) {
        System.out.println(phone);
        return this.memberRepository.findByPhone(phone);
    }
}