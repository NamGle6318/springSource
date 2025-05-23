package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    // CORS policy: No 'Access-Control-Allow-Origin' 해결 방법
    // 1. CrossOrigin(origins = "허용할 URL") 컨트롤러에 추가
    // 2. CustomServletConfig 파일 작성
    // @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("")
    public PageResultDTO<BookDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("page Request 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);

        return pageResultDTO;

    }
}
