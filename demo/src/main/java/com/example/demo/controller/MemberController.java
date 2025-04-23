package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.MemberDTO;

import lombok.extern.log4j.Log4j2;
// import com.example.demo.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequestMapping("/member")
@Controller
public class MemberController {
    // 회원가입: /member/register
    // 로그인: /member/login
    // 로그아웃: /member/logout
    // 비밀번호 변경 / member/change

    @GetMapping("/register")
    public void getRegister(@ModelAttribute("mDTO") MemberDTO memberDTO) {
        log.info("회원가입");
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("mDTO") @Valid MemberDTO memberDTO, BindingResult result,
            RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", memberDTO);

        // 유효성 검사를 통과하지 못했다면 원래 입력 페이지로 돌아가기
        if (result.hasErrors()) {
            return "/member/register";
        }

        log.info("{} {}", memberDTO.getUserid(), memberDTO.getPassword());
        log.info(memberDTO.isCheck());

        // redirect 방식으로 가면서 값을 보내고 싶다면?
        // rttr.addAttribute(rttr);
        rttr.addAttribute("userid", memberDTO.getUserid());
        rttr.addFlashAttribute("password", memberDTO.getPassword());
        rttr.addAttribute("check", memberDTO.isCheck());

        // 로그인 페이지로 이동하기
        return "redirect:/member/login";

        // return redirect : 해당 주소로 다시 "요청"하기

    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
    }

    @PostMapping("/login")
    // HttpServletRequest : 사용자의 정보, 서버의 정보 추출
    public void postLogin(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String remote = request.getRemoteAddr();
        String local = request.getLocalAddr();

        log.info("로그인 요청 {} {}", userid, password);
        log.info("클라이언트 정보 {} {}", remote, local);
    }
    // @PostMapping("/login")
    // // public void postLogin(String userid, String password) {
    // // 괄호안의 변수명들은 받아오는 value의 key 이름과 같아야함
    // public void postLogin(LoginDTO loginDTO) { // String boot에서 자동 생성자해주나봄

    // log.info("로그인 요청 {} {}", loginDTO.getUserid(), loginDTO.getPassword());
    // // void => template 찾기
    // }

    @GetMapping("logout")
    public void getLogout() {
        log.info("로그아웃");
    }

    @GetMapping("change")
    public void getChange() {
        log.info("비밀번호 변경");
    }

}
