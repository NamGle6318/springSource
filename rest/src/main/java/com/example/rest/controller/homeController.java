package com.example.rest.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class homeController {

    @GetMapping("/")
    public String getHome() {
        return "redirect:/book/list";
    }

}
