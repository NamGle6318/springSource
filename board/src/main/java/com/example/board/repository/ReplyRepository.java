package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // List<Reply> findByBoard(Board board);

    @Modifying // delete, update query문일시 무조건 사용해야 함
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBoardBno(Long bno);

    // 특정 글 조회시 달려있는 댓글 모두 가져오기
    List<Reply> findByBoardOrderByRno(Board board);
}
