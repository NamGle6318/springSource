package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.board.dto.PageRequestDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void listReplyTest() {
        List<Reply> list = replyRepository.findByBoardOrderByRno(boardRepository.findById(94L).get());
        System.out.println(list);
    }

    @Test
    public void insertMemberTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("test" + i + "@gmail.com")
                    .password(i + "1234")
                    .name("user" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void insertBoardTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long mId = (int) (Math.random() * 10) + 1;
            Member member = memberRepository.findById("user" + mId + "@gmail.com").get();
            Board board = Board.builder()
                    .title("맛있는 만화" + i + "편")
                    .content(i + "컷 만화")
                    .member(member)
                    .build();
            boardRepository.save(board);

        });
    }

    @Test
    public void insertReplyTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (int) (Math.random() * 100) + 1;

            long mId = (int) (Math.random() * 10) + 1;
            Member member = memberRepository.findById("user" + mId + "@gmail.com").get();

            Board board = boardRepository.findById(bno).get();
            Reply reply = Reply.builder()
                    .text("댓글" + i)
                    .replyer(member)
                    .board(board)
                    .build();
            replyRepository.save(reply);

        });
    }

    @Transactional
    @Test
    public void readBoardTest() {
        // bno가 2인 Board 출력
        Board board = boardRepository.findById(2L).get();
        System.out.println(board);

        // 2번 board를 작성한 작성자 출력
        Member member = memberRepository.findById(board.getMember().getEmail()).get();
        System.out.println("2번 보드를 작성한 member : " + member);
    }

    @Transactional
    @Test
    public void readReplyTest() {
        Reply reply = replyRepository.findById(2L).get();
        System.out.println(reply);
        System.out.println(reply.getBoard());

        // 게시글에 달려있는 댓글 정보를 조회 (1에서 N을 조회)
        Board board = boardRepository.findById(30L).get();
        List<Reply> replys = board.getReplys();

        for (Reply reply2 : replys) {
            System.out.println("30번 보드의 댓글들 : " + reply2);
        }
    }

    @Test
    public void selectTest() {
        PageRequestDTO poagRequestDTO = PageRequestDTO.builder()
                .page(0)
                .size(0)
                .type("tc")
                .keyword("title")
                .build();

        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.list(poagRequestDTO.getType(), poagRequestDTO.getKeyword(), pageable);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void getRowTest() {
        Object[] board = boardRepository.getBoardByBno(5L);
        System.out.println(Arrays.toString(board));
    }

}