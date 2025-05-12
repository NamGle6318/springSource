package com.example.board.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    // private final MemberService

    @GetMapping("/login")
    public void getLoginPage() {
        log.info("Login 요청");
    }

    @GetMapping("/register")
    public void getRegisterPage() {
        log.info("Register 요청");
    }

    @PostMapping("/register")
    public void postRegisterPage() {
        log.info("Register 요청");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 요청");
    }

}
