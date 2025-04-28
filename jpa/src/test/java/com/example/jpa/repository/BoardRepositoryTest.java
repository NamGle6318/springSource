package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Jetty.Accesslog.FORMAT;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Board;
import com.example.jpa.entity.QBoard;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

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
        // boardRepository.findAll().forEach(System.out::println);
        // Pageable onePage = PageRequest.of(0, 10, Sort.by("bno").descending());
        // Pageable twoPage = PageRequest.of(1, 10, Sort.by("bno").descending());
        // boardRepository.findAll(onePage).forEach(board -> System.out.println(board));
        // boardRepository.findAll(twoPage).forEach(board -> System.out.println(board));

        List<Object[]> boards = boardRepository.findByTitle2("title");
        for (Object[] object : boards) {
            System.out.println(Arrays.toString(object));
            String title = (String) object[0];
            String writer = (String) object[1];
            System.out.println("title : " + title + " writer : " + writer);
            System.out.println("------------------------------------------");

        }
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(2);
    }

    @Test
    public void queryTest() {

        // List<Board> boards = boardRepository.findByBnoGreaterThan(3L);
        // List<Board> boards = boardRepository.findByWriterLike("정현우_");

        // bno between 5 and 10

        // List<Board> boards = boardRepository.findByBnoBetween(5L, 10L);
        // List<Board> boards = boardRepository.findByBnoLessThanOrBnoGreaterThan(3L,
        // 9L);
        // List<Board> boards = boardRepository.findByBnoNotBnoBetween(3L, 9L);

        // for (Board board : boards) {
        // System.out.println(board);
        // }

        // System.out.println(boardRepository.findByWriter("정현우3"));
        // System.out.println(boardRepository.findByWriterContaining("현"));

        // System.out.println(boardRepository.hi(3L, 6L));
        // System.out.println(boardRepository.findByBnoNotBetween(3L, 6L));
        // System.out.println(boardRepository.findByBnoGreaterThan(3L));
        // System.out.println(boardRepository.findGo(3L, 9L));
        System.out.println(boardRepository.findByWriter("정현우3"));
    }

    @Test
    public void queryDSLTest() {
        // Q파일 사용
        QBoard board = QBoard.board;

        // where b.title = 'title1'
        // System.out.println(boardRepository.findAll(board.title.eq("board title1")));

        // // where b.title like 'title1%'
        // System.out.println(boardRepository.findAll(board.title.startsWith("title1")));

        // // where b.title like '%title1'
        // System.out.println(boardRepository.findAll(board.title.endsWith("title1")));

        // // where b.title like '%title%'
        // System.out.println(boardRepository.findAll(board.title.contains("title1")));

        // where b.title like '%title1% and b.bno > 0 order by bno dsc'
        // Iterable<Board> result =
        // boardRepository.findAll(board.title.contains("title1")
        // .and(board.bno.gt(0L)), Sort.by("bno").descending());
        // System.out.println(result);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> page = boardRepository.findAll(board.bno.gt(1L), pageable);
        System.out.println("page size " + page.getSize());
        System.out.println("page TotalPage " + page.getTotalPages());
        System.out.println("page TotalElements " + page.getTotalElements());
        System.out.println("page Content " + page.getContent());
    }
}
