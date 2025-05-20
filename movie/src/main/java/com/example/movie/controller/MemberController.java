package com.example.movie.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.MemberRole;
import com.example.movie.service.CustomMemberDetailsService;
import com.example.movie.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final CustomMemberDetailsService service;

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
    }

    @GetMapping("/register")
    public void getRegister(MemberDTO memberDTO) {
        log.info("회원가입 페이지 요청");

    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDTO memberDTO, BindingResult result, Model model) {
        log.info("회원가입 요청 {}", memberDTO);

        if (result.hasErrors()) {
            return "/member/register";
        }

        memberDTO.setMemberRole(MemberRole.MEMBER);
        try {
            service.register(memberDTO);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/register";
        }

        return "redirect:/member/login";
    }

    @GetMapping("/profile")
    public void getProfile() {

    }

    @GetMapping("/edit")
    public void getEdit() {

    }

    @PostMapping("/edit/nickname")
    public String postNickname(MemberDTO memberDTO, Principal principal) {
        log.info("닉네임 수정 요청 {}", memberDTO);

        memberDTO.setEmail(principal.getName());
        service.updateNickname(memberDTO);

        // SecurityContext 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();
        authMemberDTO.getMemberDTO().setNickname(memberDTO.getNickname());

        return "redirect:/member/profile";
    }

    @PostMapping("/edit/password")
    public String postPassword(PasswordDTO passwordDTO, Model model, HttpSession session) {
        log.info("비밀번호 수정 요청 {}", passwordDTO);

        try {
            service.updatePassword(passwordDTO);
            session.invalidate();
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "/member/edit";
        }

        // 비밀번호 변경시 방법
        // 1. 세션 다 끊고 다시 로그인 시키기

        // 1번 방식으로 하려면
        // HttpSession session 추가
        // session.invalidate(); 사용

        // 2. SecurityContext 비밀번호 업데이트
        return "redirect:/member/profile";
    }

    @GetMapping("/leave")
    public void getLeave() {

    }

    @PostMapping("/leave")
    public String postLeave(MemberDTO memberDTO, HttpSession session) {
        log.info("회원탈퇴 요청 정보 {}", memberDTO);
        memberService.deleteMember(memberDTO);
        session.invalidate();

        return "redirect:/movie/list";
    }

}
