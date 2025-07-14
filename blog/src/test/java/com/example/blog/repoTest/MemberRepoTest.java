package com.example.blog.repoTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.blog.entity.Board;
import com.example.blog.entity.Member;
import com.example.blog.repository.BoardRepo;
import com.example.blog.repository.MemberRepo;

@SpringBootTest
public class MemberRepoTest {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private BoardRepo boardRepo;

    @Test
    public void newMember() {
        Member member = Member.builder()
                .id("test123")
                .name("jizon")
                .build();

        memberRepo.save(member);
    }

    @Test
    public void removeMember() {
        Member author = memberRepo.findById("test123").get();
        List<Board> boards = boardRepo.findByAuthor(author);

        boards.forEach(board -> {
            boardRepo.deleteById(board.getId());
        });
        memberRepo.deleteById("test123");
    }
}
