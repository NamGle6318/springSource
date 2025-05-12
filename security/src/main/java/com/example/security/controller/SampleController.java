package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequestMapping("/sample")
@Controller
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest 요청");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
    @GetMapping("/member")
    public void getMember() {
        log.info("member 요청");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 요청");
    }

}
