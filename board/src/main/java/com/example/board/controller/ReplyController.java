package com.example.board.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDTO;

import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @PreAuthorize("permitAll")
    @GetMapping("/board/{bno}")
    public List<ReplyDTO> getList(@PathVariable Long bno) {
        log.info("bno 댓글 요청 {}", bno);

        return replyService.getList(bno);
    }

    @GetMapping("/{rno}")
    public ReplyDTO getReply(@PathVariable Long rno) {

        return replyService.select(rno);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public Long postReply(@RequestBody ReplyDTO replyDTO) {
        Long rno = replyService.create(replyDTO);
        return rno;
    }

    @PreAuthorize("authentication.name == #replyDTO.replyerEmail")
    @PutMapping("/{rno}")
    public Long putReply(@PathVariable Long rno, @RequestBody ReplyDTO replyDTO) {
        return replyService.update(replyDTO);
    }

    @PreAuthorize("authentication.name == #replyDTO.replyerEmail")
    @DeleteMapping("/{rno}")
    public Long deleteReply(@PathVariable Long rno, @RequestBody ReplyDTO replyDTO) {
        // ReplyDTO replyDTO = replyService.select(rno);

        return replyService.delete(rno);

    }

}
