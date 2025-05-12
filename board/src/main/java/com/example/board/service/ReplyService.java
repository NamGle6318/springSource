package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
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

    // 특정 보드의 댓글들 검색
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
                .replyerName(reply.getReplyer().getName())
                .replyerEmail(reply.getReplyer().getEmail())
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
                .replyer(Member.builder().email(replyDTO.getReplyerEmail()).build())
                .board(board)
                .build();

        return reply;
    }

    // 해당 계정의 권한이 매니저, 어드민 or 해당 페이지를 소유중일 경우 true를 반환하는 서비스
    // 해당 서비스를 사용하는 곳 댓글 수정과 삭제, 게시글 수정과 삭제
    public boolean hasAccess(ReplyDTO replyDTO) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // 해당 계정의 roleType이 ROLE_ADMIN or ROLE_MANAGER 여부 확인
        boolean hasRoleTypeAccess = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_MANAGER"));

        // 현재 계정의 email과 접근하려는 entity의 이메일 일치 여부 확인
        boolean hasEmailAccess = replyDTO.getReplyerEmail().equals(authentication.getName());

        return hasRoleTypeAccess || hasEmailAccess;
    }

    // TODO : hasAccess 로 댓글 수정과 삭제, 게시글 수정과 삭제를 제한하는 기능 추가해야함

}
