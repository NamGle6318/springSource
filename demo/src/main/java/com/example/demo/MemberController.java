package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequestMapping("/member")
@Controller
public class MemberController {
    // 회원가입: /member/register
    // 로그인: /member/login
    // 로그아웃: /member/logout
    // 비밀번호 변경 / member/change

    @GetMapping("/register")
    public void getRegister() {
        log.info("회원가입");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
    }

    @GetMapping("logout")
    public void getLogout() {
        log.info("로그아웃");
    }

    @GetMapping("change")
    public void getChange() {
        log.info("비밀번호 변경");
    }

}
