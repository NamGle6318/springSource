package com.example.rest.controller;

import java.util.List;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.rest.dto.MemoDTO;

import com.example.rest.service.MemoService;

import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/memo")
@RequiredArgsConstructor
@RestController
public class MemoController {
    // 주소 설계
    // 전체 메모 조회 : /memo/list

    // 서비스의 메소드 호출
    // 데이터가 전송된다면, 전송된 데이터를 Model에 담기

    private final MemoService memoService;

    @GetMapping("/list")
    public List<MemoDTO> getList(Model model) {
        List<MemoDTO> list = memoService.getList();

        model.addAttribute("list", list);
        log.info("list 요청");
        log.info(list);
        return list;
    }

    // 특정 메모만 조회 : /memo?mno=3
    @GetMapping(value = { "/read", "/update" })
    public MemoDTO getRow(Long mno, Model model) {
        log.info("조회 요청 {}", mno);
        MemoDTO memoDTO = memoService.getRow(mno);
        model.addAttribute(memoDTO);
        // template

        return memoDTO;
    }

    @PutMapping("/update")
    public Long postUpdate(@RequestBody MemoDTO memoDTO) { // 웹에 JSON으로 보이게 해주는 JACKSON은 put할땐 자동으로 안해줌 어노테이션 해야함
        log.info(memoDTO);
        Long mno = memoService.memoUpdate(memoDTO);
        
        return mno;
    }

    // 메모 수정 : /memo?mno=3
    // 메모 추가 : /memo/new
    @GetMapping("/new")
    public void getNew() {

    }

    @PostMapping("/new")
    public Long postNew(@RequestBody MemoDTO memoDTO) {
        Long mno = memoService.memoCreate(memoDTO);
        
        

        // 사용자 입력값 가져오기
        // 1. DTO
        // 2. Model
        // 3. Htpp 뭐시기

        return mno;
    }

    // 메모 삭제 : /memo/remove/3
    @DeleteMapping("/remove/{mno}")
    public Long getRemove(@PathVariable Long mno) {
        memoService.memoDelete(mno);
        return mno;
    }

    // @GetMapping("/main")
    // public void getMain() {
    // log.info("main요청!");
    // }

}
