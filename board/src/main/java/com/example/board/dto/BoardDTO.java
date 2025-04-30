package com.example.board.dto;

import java.time.LocalDateTime;

import com.example.board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // member 정보
    private String email;
    private String name;

    // 댓글개수
    private Long replyCount;

}
