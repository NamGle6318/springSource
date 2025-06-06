package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.security.security.CustomLoginSuccessHandler;

@Configuration // 환경설정 파일 어노테이션
@EnableWebSecurity
@EnableMethodSecurity // @PreAuthorize, @PostAuthorize 사용
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices)
                        throws Exception {

                // @EnableMethodSecurity 어노테이션 적용 전
                // http
                // .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/", "/sample/guest").permitAll()
                // .requestMatchers("/sample/member").hasRole("USER")
                // .requestMatchers("/sample/admin").hasRole("ADMIN")
                // .anyRequest().authenticated());

                // 커스텀 로그인 페이지 생성 전
                // .httpBasic(Customizer.withDefaults());
                // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 로그인 폼 페이지

                // @EnableMethodSecurity 어노테이션 적용 후
                http
                                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/").permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                                                .anyRequest().permitAll())
                                .formLogin(login -> login.loginPage("/member/login")
                                                .successHandler(successHandler())
                                                .permitAll());

                http
                                .logout(logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                                .logoutSuccessUrl("/"))
                                .oauth2Login(Customizer.withDefaults())
                                .oauth2Login(login -> login.successHandler(successHandler()));

                http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));

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
        @Bean
        CustomLoginSuccessHandler successHandler() {
                return new CustomLoginSuccessHandler();
        }

        // 아이디 비밀번호 기억하기
        @Bean
        RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
                RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
                TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey",
                                userDetailsService, encodingAlgorithm);
                rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
                rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 7일
                return rememberMeServices;
        }
}
