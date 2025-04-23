package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor // new TodoRegistory(이 안에 자동)
@Service // new TodoRegistory() 자동
// 결론 : 개발자가 new 생성자 할 필요 없이 container가 해준다 => 제어의 역전
public class TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public List<TodoDTO> findUnfinishedList(boolean finished) {
        List<Todo> todoList = todoRepository.findByFinished(finished);
        // // 백 -> 프론트 = entity -> DTO

        // List<TodoDTO> todoDTOList = new ArrayList<>();
        // todoList.forEach(todo -> {
        // TodoDTO todoDTO = (modelMapper.map(todo, TodoDTO.class));
        // todoDTOList.add(todoDTO)
        // });
        // return todoDTOList;

        List<TodoDTO> todoDTOList = todoList.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        return todoDTOList;
    }

}
