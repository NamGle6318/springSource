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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/todo")
@Controller
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/list")
    public void getUnfinishedList(@RequestParam(defaultValue = "0") boolean finished, Model model) {
        // 미완료된 todoList들을 가져오기 todoDTOList
        List<TodoDTO> todoDTOList = todoService.findfinishedList(finished);
        model.addAttribute("todoDTOList", todoDTOList);

        // 어떤(완료, 미완료) 목록을 보여주는가?
        model.addAttribute("finished", finished);

        // log.info("미완료 todo 가져오기 " + finished);
        // for (TodoDTO todoDTO : todoDTOList) {
        // log.info(todoDTO);
        // }
    }

    @PostMapping("/list")
    public void postUnfinishedList() {

        // return entity;
    }

    @PostMapping("/modify")
    public String postFinishedList(TodoDTO todoDTO, RedirectAttributes rttr) {
        log.info("수정 {}", todoDTO);
        Long id = todoService.changeFinished(todoDTO);
        rttr.addAttribute("id", id);
        return "redirect:/todo/read";
    }

    @GetMapping("/read")
    public void getRead(Long id, Model model) {
        log.info("조회 {}", id);
        TodoDTO todoDTO = todoService.read(id);
        model.addAttribute("todoDTO", todoDTO);

    }

    @GetMapping("/remove")
    public String getRemove(Long id) {
        log.info("삭제할거임" + id);
        todoService.delete(id);

        return "redirect:/todo/list";
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("create 작성 요청");

        // return new String();
    }

    @PostMapping("/create")
    public String postCreate(TodoDTO todoDTO, RedirectAttributes rttr) {
        log.info("create 작성 완료 확인 {}", todoDTO);
        Long id = todoService.create(todoDTO);
        rttr.addAttribute("id", id);

        return "redirect:/todo/read";
        // return new String();
    }

}
// <!-- <tr th:each="dto : ${list}">
// <td th:text="${dto.mno}"></td>
// <td><a th:href="@{/memo/read(mno=${dto.mno})}"
// th:text="${dto.memoText}"></a></td>
// <td th:text="${#temporals.format(dto.createdDate, 'yyyy-MM-dd')}"></td>
// </tr> -->