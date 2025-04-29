package com.example.book.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.repository.BookRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/book")
@Controller
public class bookController {

    private final BookService bookService;

    @GetMapping("/list")
    public void getHome(Model model, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("page Request 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        model.addAttribute("result", pageResultDTO);

        // model.addAttribute("num", rttr.getAttribute("num"));

    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code, Model model, PageRequestDTO pageRequestDTO) {
        log.info("book get 요청 {} ", code);
        BookDTO bookDTO = bookService.read(code);
        model.addAttribute("bookDTO", bookDTO);
        // GET -> MODIFY 동일경로로 이동시 데이터 자동으로 보내줌.
    }

    @PostMapping("/modify")
    public String postModify(BookDTO bookDTO, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("Modify 요청 {} ", bookDTO);
        Long code = bookService.modify(bookDTO);
        log.info("Modify 변경 {} ", bookDTO);
        rttr.addAttribute("code", code);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long code, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("remove 요청 {} ", code);
        bookService.delete(code);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/list";
    }

    @GetMapping("/create")
    public void getCreate(BookDTO bookDTO, PageRequestDTO pageRequestDTO) {
        log.info("create 요청");

    }

    @PostMapping("/create")
    public String postCreate(@Valid BookDTO bookDTO, BindingResult result, RedirectAttributes rttr) {
        if (result.hasErrors()) {

            return "/book/create";
        }
        Long num = bookService.create(bookDTO);
        rttr.addFlashAttribute("num", num);
        log.info("create 요청 {} ", bookDTO);
        return "redirect:/book/list";
    }

    // 동일한 주소의 매핑을 만들 시 문법적으로는 에러가 나지 않지만, 실행시 에러남
    // Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map

    // @PostMapping("/remove")
    // public void postRemove1(Long code) {
    // log.info("remove 요청 {} ", code);
    // }

}
