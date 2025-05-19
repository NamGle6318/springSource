package com.example.movie.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

@Getter

@ToString(exclude = "movieImages")
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Setter
    @Column(nullable = false)
    private String title;

    @ElementCollection
    @Builder.Default
    private List<MovieImage> movieImages = new ArrayList<>();

    // movieImages List에 movieImage 추가
    public void addImage(MovieImage movieImage) {
        movieImage.setOrd(this.movieImages.size());
        movieImages.add(movieImage);

    }

    // movieImages List 초기화
    public void clearList() {
        this.movieImages.clear();
    }
}
