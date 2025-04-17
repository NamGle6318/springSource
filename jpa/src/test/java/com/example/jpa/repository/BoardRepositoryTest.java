package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertTest() {

        IntStream.rangeClosed(1, 10).forEach(i -> {

            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("정현우" + i)
                    .build();

            boardRepository.save(board);
        });

    }

    @Test
    public void updateTest() {
        Board board = boardRepository.findById(1).get();
        board.setWriter("정현우아님");

        boardRepository.save(board);
    }

    @Test
    public void readTest() {
        Board board = boardRepository.findById(1).get();
        System.out.println(board);
    }

    @Test
    public void listTest() {
        boardRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(2);
    }
}
