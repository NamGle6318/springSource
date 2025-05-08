package com.example.rest.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.rest.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.repository.BookRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 일반 컨트롤러 + Rest 둘 다 하는 방법
// 데이터만 내보내기 : ResponseEntity or @ResponseBody

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/book")
@Controller
public class bookController {

    private final BookService bookService;

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO>> getHome(PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("page Request 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);

        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping({ "/read/{code}", "/modify/{code}" })
    public BookDTO getRead(@PathVariable Long code, PageRequestDTO pageRequestDTO) {
        log.info("book get 요청 {} ", code);
        BookDTO bookDTO = bookService.read(code);

        return bookDTO;
    }

    @ResponseBody
    @PutMapping("/modify")
    public Long postModify(@RequestBody BookDTO bookDTO, PageRequestDTO pageRequestDTO) {
        log.info("Modify 요청 {} ", bookDTO);
        bookService.modify(bookDTO);
        log.info("Modify 변경 {} ", bookDTO);

        return bookDTO.getCode();
    }

    @DeleteMapping("/remove/{code}")
    public ResponseEntity<Long> postRemove(@PathVariable Long code, PageRequestDTO pageRequestDTO) {
        log.info("remove 요청 {} ", code);
        bookService.delete(code);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/create")
    public void getCreate(BookDTO bookDTO, PageRequestDTO pageRequestDTO) {
        log.info("create 요청");

    }

    // rest controller가 됨
    @ResponseBody
    @PostMapping("/create")
    public Long postCreate(@RequestBody BookDTO bookDTO) {
        Long num = bookService.create(bookDTO);

        log.info("create 요청 {} ", bookDTO);
        return num;
    }

    // 동일한 주소의 매핑을 만들 시 문법적으로는 에러가 나지 않지만, 실행시 에러남
    // Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map

    // @PostMapping("/remove")
    // public void postRemove1(Long code) {
    // log.info("remove 요청 {} ", code);
    // }

}
