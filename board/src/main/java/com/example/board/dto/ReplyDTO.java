package com.example.board.dto;

import java.time.LocalDateTime;

import groovy.transform.ToString;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    private Long rno;
    private String text;
    private String replyerEmail;
    private String replyerName;

    // 게시글 번호(부모 번호)
    private Long bno;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
