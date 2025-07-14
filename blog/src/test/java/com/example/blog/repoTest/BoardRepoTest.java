package com.example.blog.repoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.blog.entity.Board;
import com.example.blog.entity.Member;
import com.example.blog.repository.BoardRepo;
import com.example.blog.repository.MemberRepo;

@SpringBootTest
public class BoardRepoTest {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private BoardRepo boardRepo;

    @Test
    public void newBoard() {
        Member author = memberRepo.findById("test123").get();

        Board board = Board.builder()
                .title("게시글")
                .content("나는 훔치다 굶주림음 나는 훔치다 굶주림음 나는 훔치다 굶주림음 나는 훔치다 굶주림음")
                .author(author)
                .build();

        boardRepo.save(board);
    }

    @Test
    public void updateBoard() {
        Board board = boardRepo.findById(4L).get();

        board.setContent("나는 훔치다 굶주림을");
        boardRepo.save(board);
    }

    @Test
    public void removeBoard() {
        boardRepo.deleteById(1L);
    }
}
