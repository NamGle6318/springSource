package com.example.board.dto;

import java.time.LocalDateTime;

import com.example.board.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "내용을 입력해 주세요")
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // member 정보
    @Email(message = "이메일 형식을 확인해 주세요")
    @NotBlank(message = "이메일을 입력해 주세요")
    private String email;
    private String name;

    // 댓글개수
    private Long replyCount;

}
