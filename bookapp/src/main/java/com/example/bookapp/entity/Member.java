package com.example.bookapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Member {

    @Id
    private String email;
    private String pw;
    private String nickname;
    private boolean social;

}
