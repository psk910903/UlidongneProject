package com.study.UlidongneProject.config;

import com.study.UlidongneProject.entity.MemberEntity;
import com.study.UlidongneProject.entity.repository.MemberRepository;
import com.study.UlidongneProject.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //웹보안 활성화를위한 annotation
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final private SecurityService securityService;
    private final MemberRepository memberRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http
//                .csrf().disable() //토큰 무효화
                .authorizeRequests() // 요청에 대한 보안설정을 시작
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/join/**").permitAll() //루트경로 아래 모든 요청을 허가한다
                .anyRequest().authenticated() //그외 어떤 요청에도 인증를 한다.
        .and()
                .formLogin() //로그인 인증에 대한 설정을 시작
                .loginPage("/loginForm") //로그인 페이지를 /loginForm URL로 하겠다.
                .loginProcessingUrl("/loginAction") //로그인 액션 URI를 지정한다.
                .successHandler( (request,response,authentication) -> {
                    System.out.println("서서서서서서서성공");
                    String memberName = request.getParameter("username");
                    MemberEntity entity = memberRepository.findByPhone(memberName);
                    request.getSession().setAttribute("memberIdx", entity.getMemberIdx());
                    response.sendRedirect("/");
//
                    Socket socket = new Socket("localhost", 8080); // 서버에 연결하는 소켓 생성

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 출력 스트림

                    String message = request.getParameter("username") + "," + request.getParameter("password");
                    writer.write(message);
                    writer.flush(); // 서버에게 메시지 송신
                    socket.close(); // 소켓 닫기
//

                })
                .failureUrl("/loginForm?error")
                .permitAll() //로그인 페이지를 모두에게 허용한다.
        //로그아웃
        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutAction"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                //SNS 로그인
        .and()
                .oauth2Login()
                .successHandler(successHandler())
                .failureHandler(failureHandler());
                //oauth2Login에 성공하면 customOAuth2UserService에서 설정을 진행하겠다라는 의미
//                .userInfoEndpoint().userService(service3);
    }
    //BCrypt 암호화 엔코더 빈 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/googleLoginSuccess");
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/googleLoginFailure");
    }

    //UserDetailsService 인터페이스 구현체를 설정한다.
    // - 내부의 loadUserByUsername 메소드를 통해, 로그인 인증결과를 가져온다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(passwordEncoder());
    }
}