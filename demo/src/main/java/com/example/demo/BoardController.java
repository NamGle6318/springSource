package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequestMapping("/board")
@Controller
public class BoardController {
    @GetMapping("/create") // http://localhost:8080/board/create
    public void getCreate() {
        log.info("create 요청");
        // return "board/create";
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
