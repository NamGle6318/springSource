package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void insertTodoTest() {
        // todo에 임의 값 추가하기
        IntStream.range(1, 10).forEach(i -> {
            Todo todo = Todo.builder()
                    .content("강아지" + i + "마리 산책")
                    .importanted(true)
                    .finished(false)
                    .build();

            todoRepository.save(todo);
        });
    }

    @Test
    public void readAllTest() {
        // todo 리스트 전체 출력하기
        for (Todo todo : todoRepository.findAll()) {
            System.out.println(todo);
        }
    }

    @Test
    public void readAllTest2() {
        // todo 리스중 완료된 항목만 출력하기
        for (Todo todo : todoRepository.findByFinished(true)) {
            System.out.println(todo);
        }
    }

    @Test
    public void readAllTest3() {
        // todo 리스중 완료된 항목만 출력하기
        for (Todo todo : todoRepository.findByImportanted(false)) {
            System.out.println(todo);
        }
    }

    @Test
    public void deleteTest() {
        // 5번 todo 삭제하기
        todoRepository.deleteById(5L);
    }

    @Test
    public void updateTest() {
        // 3번 TODO의 완료여부를 1로 수정해보기
        Todo todo = todoRepository.findById(3L).get();
        todo.setFinished(true);
        todoRepository.save(todo);
    }
}
