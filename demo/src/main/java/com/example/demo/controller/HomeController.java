package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
public class HomeController {
    // http://localhost:8080/
    @GetMapping("/")
    public String getHome() {
        log.info("home 요청"); // system.out과 같은 개념 @Log4j2로 사용가능해짐
        return "home";
    }

    // http://localhost:8080/basic/
    @GetMapping("/basic")
    public String getInfo() {
        return "info";
    }

    @PostMapping("/basic")
    public String postInfo(@ModelAttribute("num1") int num1, @ModelAttribute("num2") int num2, Model model) {

        // model.addAttribute("name", value) name은 화면단에서 사용, 중복불가 : 객체생성
        // model.addAttribute("result", result);
        // model.addAttribute("num1", number1);
        // model.addAttribute("num2", num2);

        // return 할때 result도 가져감
        return "info";
        // void로 가면 주소 = localhost:8080/basic의 주소를 찾음 -> info.html은 basic 폴더에 따로 있는게 아님
        // info.html이 없어서 에러코드 500 발생?

    }

}
