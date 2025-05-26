package com.example.bookapp.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Novel {
    @Id
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate; // 출판일
    private boolean available; // 구매 가능 여부
    private int grade; // 평점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENRE_ID")
    private Genre genre;
    // private String genre; // 장르명
}
