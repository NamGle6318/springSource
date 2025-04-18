package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.service.MemoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequestMapping("/memo")
@RequiredArgsConstructor
@Controller
public class MemoController {
    // 주소 설계
    // 전체 메모 조회 : /memo/list

    // 서비스의 메소드 호출
    // 데이터가 전송된다면, 전송된 데이터를 Model에 담기

    private final MemoService memoService;

    @GetMapping("/list")
    public void getList(Model model) {
        List<MemoDTO> list = memoService.getList();

        model.addAttribute("list", list);
        log.info("list 요청");
        log.info(list);

    }

    // 특정 메모만 조회 : /memo?mno=3
    @GetMapping(value = { "/read", "/update" })
    public void getRow(Long mno, Model model) {
        log.info("조회 요청 {}", mno);
        MemoDTO memoDTO = memoService.getRow(mno);
        model.addAttribute(memoDTO);
        // template
    }

    @PostMapping("/update")
    public String postUpdate(MemoDTO memoDTO, RedirectAttributes rttr) {
        log.info(memoDTO);
        Long mno = memoService.memoUpdate(memoDTO);
        rttr.addAttribute("mno", mno);
        return "redirect:/memo/read";
    }

    // 메모 수정 : /memo?mno=3
    // 메모 추가 : /memo/new
    @GetMapping("/new")
    public void getNew() {

    }

    @PostMapping("/new")
    public String postNew(MemoDTO memoDTO, RedirectAttributes rttr) {
        long mno = memoService.memoCreate(memoDTO);
        log.info("새 메모 작성 : " + memoDTO.getMemoText());
        log.info("생성된 번호 : " + mno);
        rttr.addFlashAttribute("msg", mno);

        // 사용자 입력값 가져오기
        // 1. DTO
        // 2. Model
        // 3. Htpp 뭐시기

        return "redirect:/memo/list";
    }

    // 메모 삭제 : /memo/remove?mno=3
    @GetMapping("/remove")
    public String getRemove(Long mno) {
        memoService.memoDelete(mno);
        return "redirect:/memo/list";
    }

    // @GetMapping("/main")
    // public void getMain() {
    // log.info("main요청!");
    // }

}
