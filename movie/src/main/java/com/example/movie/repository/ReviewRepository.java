package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying // delete, update시 반드시 작성
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);

    @Query("delete from Review r where r.member = :member")
    @Modifying
    void deleteByMember(Member member);

    // 해당 영화의 id를 통해 관련 리뷰 정보 불러오기
    @Query("SELECT r FROM Review r WHERE r.movie = :movie")
    // join문을 사용 시키기
    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH)
    List<Review> findByReview(Movie movie);

}
