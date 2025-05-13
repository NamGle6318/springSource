package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.security.AuthMemberDTO;
import com.example.board.security.MemberDTO;
import com.example.board.security.MemberDetailsService;

import jakarta.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberDetailsService memberDetailsService;

    @GetMapping("/login")
    public void getLoginPage() {
        log.info("Login 요청");
    }

    @GetMapping("/register")
    public void getRegisterPage(MemberDTO memberDTO) {
        log.info("Register 요청 {}", memberDTO);
    }

    @PostMapping("/register")
    public String postRegisterPage(@Valid MemberDTO memberDTO, BindingResult result, Model model) {
        log.info("Register 요청 {} : ", memberDTO);

        if (result.hasErrors()) {
            return "/member/register";
        }
        try {
            memberDetailsService.register(memberDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/register";
        }
        return "redirect:/member/login";
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 요청");
    }

}
