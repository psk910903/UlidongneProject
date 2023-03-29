package com.study.UlidongneProject.service;

import com.study.UlidongneProject.entity.*;
import com.study.UlidongneProject.entity.repository.CategoryRepository;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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



//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
//                .getUserInfoEndpoint().getUserNameAttributeName();
//
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//        SnsUser user = saveOrUpdate(attributes);
//        httpSession.setAttribute("user", new SessionUser(user));
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());
//    }

//
//    private SnsUser saveOrUpdate(OAuthAttributes attributes) {
//        SnsUser user = userRepository.findByEmail(attributes.getEmail())
//                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }

    //시큐리티, sns 로그인 -----------------------------------------------------------------

}