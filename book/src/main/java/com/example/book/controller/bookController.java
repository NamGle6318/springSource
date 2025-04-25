package com.example.book.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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
    public void getHome(Model model, RedirectAttributes rttr) {
        log.info("book list 요청");
        List<BookDTO> bookDTOs = bookService.readAll();
        model.addAttribute("bookDTOs", bookDTOs);
        model.addAttribute("num", rttr.getAttribute("num"));

    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code, Model model) {
        log.info("book get 요청 {} ", code);
        BookDTO bookDTO = bookService.read(code);
        model.addAttribute("bookDTO", bookDTO);

    }

    @PostMapping("/modify")
    public String postModify(BookDTO bookDTO, RedirectAttributes rttr) {
        log.info("Modify 요청 {} ", bookDTO);
        Long code = bookService.modify(bookDTO);
        log.info("Modify 변경 {} ", bookDTO);
        rttr.addAttribute("code", code);

        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long code) {
        log.info("remove 요청 {} ", code);
        bookService.delete(code);
        return "redirect:/book/list";
    }

    @GetMapping("/create")
    public void getCreate(BookDTO bookDTO) {
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
