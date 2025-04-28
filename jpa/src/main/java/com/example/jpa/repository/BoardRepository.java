package com.example.jpa.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>, QuerydslPredicateExecutor<Board> {

    // @Query("SELECT BOARD.BNO FROM BOARD WHERE TITLE LIKE 'title_'")
    // List<Board> findByTitle(String title);

    // List<Board> findByTitleLike(String title);

    // List<Board> findByUserLike(String title);

    // List<Board> findByWriterContaing(String title);

    // List<Board> findByContentContaing(String title);

    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // List<Board> findByBnoIn(Collection<Integer> bno);

    // List<Board> findByBnoGreaterThan(Long bno);

    // List<Board> findByWriterLike(String writer);

    // List<Board> findByBnoBetween(Long start, Long end);

    // List<Board> findByBnoLessThanOrBnoGreaterThan(Long down, Long up);

    // List<Board> findByBnoNotBnoBetween(Long start, Long end);

    @Query("SELECT b FROM Board b WHERE b.writer = :writer")
    List<Board> findByWriter(String writer);

    // @Query("select b from Board b where b.writer like %?1%")
    // List<Board> findByWriterContaining(String writer);

    @Query("select b from Board b where b.bno between ?1 and ?2")
    List<Board> hi(Long start, Long stop);

    // List<Board> findByBnoNotBetween(Long start, Long stop);

    @Query(value = "select * from Board b where b.bno > ?1", nativeQuery = true)
    List<Board> findByBnoGreaterThan(Long bno);

    @Query("select b from Board b where b.bno not between ?1 and ?2")
    List<Board> findGo(Long start, Long stop);

    @Query("SELECT b.title, b.writer FROM Board b WHERE b.title like %?1%")
    List<Object[]> findByTitle2(String title);
}
