package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.todo.dto.TodoDTO;
import com.example.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/todo")
@Controller
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/list")
    public void getList(@RequestParam(defaultValue = "0") boolean finished, Model model) {
        List<TodoDTO> todoDTOList = todoService.findUnfinishedList(false);
        model.addAttribute("todoDTOList", todoDTOList);

        log.info("미완료 todo 가져오기 " + finished);
        for (TodoDTO todoDTO : todoDTOList) {
            log.info(todoDTO);
        }
    }

}
