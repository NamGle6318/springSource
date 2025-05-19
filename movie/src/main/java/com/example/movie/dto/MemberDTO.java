package com.example.movie.dto;

import com.example.movie.entity.MemberRole;

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
public class MemberDTO {

    private Long mid;

    private String email;
    private String password;
    private String nickname;

    private MemberRole memberRole;
}
