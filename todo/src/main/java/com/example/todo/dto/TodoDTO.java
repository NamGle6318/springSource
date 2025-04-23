package com.example.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TodoDTO {
    private Long id;
    private String content;
    private boolean finished;
    private boolean importanted;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
