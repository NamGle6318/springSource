package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class HomeController {
    // http://localhost:8080/
    @GetMapping("/")
    public String getHome() {
        log.info("home 요청"); // system.out과 같은 개념 @Log4j2로 사용가능해짐
        return "home";
    }

    // http://localhost:8080/basic/
    @GetMapping("/basic")
    public String getMethodHome() {
        return "info";
    }

}
