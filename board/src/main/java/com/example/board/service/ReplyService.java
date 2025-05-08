package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReplyService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    // 특정 보드의 댓글 검색
    public List<ReplyDTO> getList(Long bno) {

        Board board = boardRepository.findById(bno).get();

        List<Reply> list = replyRepository.findByBoardOrderByRno(board);

        // Reply => ReplyDTO로 변경
        return list.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    // 특정 댓글 검색
    public ReplyDTO select(Long rno) {

        Reply reply = replyRepository.findById(rno).get();
        ReplyDTO replyDTO = entityToDto(reply);

        return replyDTO;
    }

    // 댓글 삽입

    public Long create(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);

        return reply.getRno();
    }

    // 댓글 수정
    public Long update(ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyDTO.getRno()).get();
        reply.setText(replyDTO.getText());
        replyRepository.save(reply);

        return replyDTO.getRno();
    }

    // 댓글 삭제
    public Long delete(ReplyDTO replyDTO) {
        replyRepository.deleteById(replyDTO.getRno());

        return replyDTO.getRno();
    }

    private ReplyDTO entityToDto(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .bno(reply.getBoard().getBno())
                .createdDate(reply.getCreatedDate())
                .updatedDate(reply.getUpdatedDate())
                .build();

        return replyDTO;
    }

    private Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = boardRepository.findById(replyDTO.getBno()).get();
        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

}
