package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController

public class TodoRestController {
    private final TodoService todoService;

    // todoList 들고 오기
    @GetMapping("/list")
    public List<TodoDTO> getTodos() {
        log.info("전체 todos 가져오기");
        List<TodoDTO> todos = todoService.list2();

        return todos;

    }

    // Todo 생성
    @PostMapping("/add")
    public TodoDTO postFinishedList(@RequestBody TodoDTO todoDTO) {
        log.info("수정 {}", todoDTO);
        TodoDTO newTodo = todoService.create2(todoDTO);
        return newTodo;

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        todoService.delete(id);

        return "success";
    }

    @PutMapping("/{id}")
    public Long postCompleted(@PathVariable Long id) {
        TodoDTO dto = todoService.read(id);
        todoService.changeFinished2(dto);

        return dto.getId();
    }

    // -------------------

    @GetMapping("/read")
    public void getRead(Long id, Model model) {
        log.info("조회 {}", id);
        TodoDTO todoDTO = todoService.read(id);
        model.addAttribute("todoDTO", todoDTO);

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

    // @PostMapping("/list")
    // public void postUnfinishedList() {

    // // return entity;
    // }

}
// <!-- <tr th:each="dto : ${list}">
// <td th:text="${dto.mno}"></td>
// <td><a th:href="@{/memo/read(mno=${dto.mno})}"
// th:text="${dto.memoText}"></a></td>
// <td th:text="${#temporals.format(dto.createdDate, 'yyyy-MM-dd')}"></td>
// </tr> -->