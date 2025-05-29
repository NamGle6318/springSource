package com.example.bookapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookapp.dto.NovelDTO;
import com.example.bookapp.dto.PageRequestDTO;
import com.example.bookapp.dto.PageResultDTO;
import com.example.bookapp.service.NovelService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/books")
@RequiredArgsConstructor
@RestController
public class NovelRestController {
    // 전체조회, 하나조회, 입력, 수정, 삭제
    // 전체조회 : http://localhost:8080/api/books
    // 하나조회 : http://localhost:8080/api/books/1
    // 입력 : http://localhost:8080/api/books/add
    // 수정 : http://localhost:8080/api/books/1
    // 삭제 : http://localhost:8080/api/books/1

    private final NovelService novelService;

    // 홈 화면
    @GetMapping("")
    public PageResultDTO<NovelDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("전체 도서 요청 {}", pageRequestDTO);
        PageResultDTO<NovelDTO> result = novelService.getList(pageRequestDTO);
        log.info(result);
        return result;
    }

    @GetMapping("/{id}")
    public NovelDTO getRow(@PathVariable Long id) {
        log.info("도서 get{}", id);
        NovelDTO novelDTO = novelService.getRow(id);
        return novelDTO;
    }

    @PutMapping("/{id}")
    public Long putNovel(@RequestBody NovelDTO novelDTO) {
        log.info("수정 {}", novelDTO);

        return novelService.avaUpdate(novelDTO);
    }

    @PutMapping("/edit/{id}")
    public Long putPubNovel(@RequestBody NovelDTO novelDTO) {
        log.info("수정 {}", novelDTO);

        return novelService.pubUpdate(novelDTO);
    }

    @PostMapping("add")
    public Long postNovel(@RequestBody NovelDTO novelDTO) {
        log.info("생성 요청 {}", novelDTO);

        return novelService.novelInsert(novelDTO);
    }

    @DeleteMapping("{id}")
    public Long removeNovel(@PathVariable Long id) {
        NovelDTO novelDTO = novelService.getRow(id);
        return novelService.novelRemove(novelDTO);
    }

}
