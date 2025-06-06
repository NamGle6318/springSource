package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("/member")
@Log4j2
@Controller
public class MemberController {

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLoginPage() {
        log.info("로그인 요청");
    }
}
