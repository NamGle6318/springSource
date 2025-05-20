package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// import com.example.movie.security.CustomLoginSuccessHandler;

// import com.example.security.security.CustomLoginSuccessHandler;

@Configuration // 환경설정 파일 어노테이션
@EnableWebSecurity
@EnableMethodSecurity // @PreAuthorize, @PostAuthorize 사용
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/assets/**", "/upload/**")
                                .permitAll()
                                .requestMatchers("/movie/list", "/movie/read", "/auth")
                                .permitAll()
                                .requestMatchers("/reviews/**", "/upload/display/**")
                                .permitAll()
                                .requestMatchers("/member/register")
                                .permitAll()
                                .anyRequest().authenticated());

                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

                // http.csrf(csrf -> csrf.disable()); // crud test를 위한 임시 비활성화

                http.formLogin(login -> login.loginPage("/member/login")
                                .defaultSuccessUrl("/", true)
                                // .successHandler(successHandler())

                                .permitAll());

                http
                                .logout(logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                                .logoutSuccessUrl("/"));

                // http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));

                return http.build();
        }

        @Bean // @Bean : 생성된 객체는 Spring container에 영속화됨
        PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        // @Bean
        // // user의 name, password, roles 정보 주입하여 SecurityContextHolder에 저장시키기 (DB사용 안하는
        // 중)
        // UserDetailsService users() {
        // UserDetails user = User.builder()
        // .username("user")
        // .password("{bcrypt}$2a$10$pV22sjmXoQCSv1ibjXxy1udOAuQwTa/yzBxtkOjswovwiN.CoY1Qa")
        // // = 1111
        // .roles("USER") // ROLE_권한명
        // .build();
        // UserDetails admin = User.builder()
        // .username("admin")
        // .password("{bcrypt}$2a$10$pV22sjmXoQCSv1ibjXxy1udOAuQwTa/yzBxtkOjswovwiN.CoY1Qa")
        // // = 1111
        // .roles("USER", "ADMIN") // ROLE_권한명
        // .build();
        // return new InMemoryUserDetailsManager(user, admin);
        // }

        // 로그인 성공 시 핸들러
        // @Bean
        // CustomLoginSuccessHandler successHandler() {
        // return new CustomLoginSuccessHandler();
        // }

        // // 아이디 비밀번호 기억하기
        // @Bean
        // RememberMeServices rememberMeServices(UserDetailsService userDetailsService)
        // {
        // RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        // TokenBasedRememberMeServices rememberMeServices = new
        // TokenBasedRememberMeServices("myKey",
        // userDetailsService, encodingAlgorithm);
        // rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        // rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 7일
        // return rememberMeServices;
        // }
}
