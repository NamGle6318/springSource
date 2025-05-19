package com.example.movie.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long rno;
    private int grade;
    private String text;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // member 정보
    private Long mid;
    private String email;
    private String nickname;

    // movie 정보
    private Long mno;

}
