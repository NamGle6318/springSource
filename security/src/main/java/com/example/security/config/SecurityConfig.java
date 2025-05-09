package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 환경설정 파일 어노테이션
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/sample/guest").permitAll()
                        .requestMatchers("/sample/member").hasRole("USER")
                        .requestMatchers("/sample/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // .httpBasic(Customizer.withDefaults());
                // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 로그인 폼 페이지
                .formLogin(login -> login.loginPage("/member/login")
                        .permitAll());

        http
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/"));

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
}
