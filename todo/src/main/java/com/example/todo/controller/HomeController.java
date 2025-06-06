package com.example.todo.controller;

import org.springframework.stereotype.Controller;

import com.example.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class HomeController {
    // private final TodoService todoService;

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

}
