package com.example.rest.dto;

import java.time.LocalDateTime;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoDTO {
    private Long mno;
    private String memoText;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}