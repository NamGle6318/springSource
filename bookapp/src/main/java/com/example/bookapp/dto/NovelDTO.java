package com.example.bookapp.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class NovelDTO {

    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate; // 출판일
    private boolean available; // 구매 가능 여부

    // 장르
    private Long gid;
    private String genreName;
    private Integer rating;

}
