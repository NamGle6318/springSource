package com.example.movie.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieImageDTO {
    private Long inum;

    private String uuid;
    private String imgName;
    private String path;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
