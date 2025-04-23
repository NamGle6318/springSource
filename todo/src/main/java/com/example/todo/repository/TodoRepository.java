package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // select 문을 대신할 메소드 생성
    // 완료/미완료 목록 조회
    // 여기다가 메소드 만들어낼 수 있고, 불러다 쓸 수 있게 됨
    List<Todo> findByFinished(boolean finished);

    List<Todo> findByImportanted(boolean importanted);
}
