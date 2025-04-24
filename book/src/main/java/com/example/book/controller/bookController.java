package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.book.dto.BookDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/book")
@Controller
public class bookController {

    @GetMapping("/list")
    public void getHome() {
        log.info("book list 요청");
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code) {
        log.info("book get 요청 {} ", code);
    }

    @PostMapping("/modify")
    public void postModify(BookDTO bookDTO) {
        log.info("Modify 요청 {} ", bookDTO);
    }

    @PostMapping("/remove")
    public void postRemove(Long code) {
        log.info("remove 요청 {} ", code);
    }

}
