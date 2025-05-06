package com.example.diary.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.diary.entity.Diary;
import com.example.diary.service.DiaryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@Log4j2
@Controller
public class HomeController {
    
    // @Autowired
    private final DiaryService diaryService;

    @GetMapping("/")
    public String getHome() {
        return "/main";
    }
    
@GetMapping("/add")
public void getAdd() {
    // log.info("add 페이지 요청");
}

@PostMapping("/add")    
public String postAdd(String title, String content) {
    // log.info("add 페이지 post");
    // log.info("title {}, content{}", title, content);
    diaryService.addDiary(title, content);
    return "/list";
}

@GetMapping("/list")
public void getList(Model model) {
    List<Diary> list = diaryService.list();
    model.addAttribute("diarys", list);
}
@PostMapping("/list")
public void postList(Model model, String startDate, String endDate) {
    String time = " 00:00:00";
    startDate += time;
    endDate += time;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
    LocalDateTime start = LocalDateTime.parse(startDate, formatter);
    LocalDateTime end = LocalDateTime.parse(startDate, formatter);

    
    
    
}




}
