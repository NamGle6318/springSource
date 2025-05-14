package com.example.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString(exclude = { "member", "movie" })
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private int grade;

    private String text;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Movie movie;
}
