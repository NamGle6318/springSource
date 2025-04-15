package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/board")
@Controller
public class BoardController {
    @GetMapping("/create") // http://localhost:8080/board/create
    public void getCreate() {
        log.info("create 요청");

        // return "board/create";

    }

    @PostMapping("/create")
    public void postCreate(String name, HttpSession session) {

        session.setAttribute("name1", name);

        // log.info(name);
        // 어느 페이지로 이동하던지 name을 유지하려면
        // 1. model.addAttribute("name", name);
        // 2. (@ModelAttribute("name") String name)
        // 3. DTO 생성

        // redirect로 이동하는데 name 값을 유지하려면?
        // RedirectAttribute.addAttribute("name", name) -> [[${param.name}]]
        // rttr.addAttribute("name", name);
        // return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void getList() {
        log.info("list 요청");
        // return "board/list";
    }

    @GetMapping("/read")
    public void getRead() {
        log.info("read 요청");
        // return "board/read";
    }

    @GetMapping("/update")
    public void getUpdate() {
        log.info("update 요청");
        // return "board/update";
    }

}
