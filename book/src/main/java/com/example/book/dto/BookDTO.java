package com.example.book.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long code;
    @NotBlank(message = "도서명을 입력해주세요.")
    private String title;
    @NotBlank(message = "작가명을 입력해주세요.")
    private String author;
    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
