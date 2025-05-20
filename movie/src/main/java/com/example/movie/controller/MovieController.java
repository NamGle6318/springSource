package com.example.movie.controller;

import java.util.Arrays;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Movie;
import com.example.movie.service.MovieService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequestMapping("/movie")
@RequiredArgsConstructor
@Controller
public class MovieController {

    private final MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public void getCreate(PageRequestDTO pageRequestDTO) {

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String postCreate(MovieDTO movieDTO, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("영화 등록 요청 {}", movieDTO);

        Long mno = movieService.createMovie(movieDTO);
        rttr.addAttribute("mno", mno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/movie/read";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("영화 리스트 요청 {}", pageRequestDTO);

        PageResultDTO<MovieDTO> result = movieService.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long mno, PageRequestDTO pageRequestDTO, Model model) {

        MovieDTO movieDTO = movieService.getRow(mno);

        model.addAttribute("dto", movieDTO);

    }

    @PostMapping("/delete")
    public String removeMovie(Long mno, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {

        movieService.deleteRow(mno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/movie/list";
    }

}
