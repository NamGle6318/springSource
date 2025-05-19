package com.example.movie.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void findReviewTest() {
        // 2번 영화의 리뷰 불러오기
        Movie movie = movieRepository.findById(2L).get();

        List<Review> list = reviewRepository.findByReview(movie);

        for (Review review : list) {
            System.out.println(review);
        }
    }

    @Transactional
    @Test
    public void findReview2Test() {
        // 2번 영화의 리뷰를 작성한 멤버들의 이메일 정보 불러오기
        Movie movie = movieRepository.findById(2L).get();

        List<Review> list = reviewRepository.findByReview(movie);

        for (Review review : list) {
            System.out.println(review.getMember().getEmail());
        }
    }

    // @Transactional repository에 join문으로 설정을 해놔서 transactional 사용 필요가 없음
    @Test
    public void findReview3Test() {
        // 2번 영화의 리뷰를 작성한 멤버들의 이메일 정보 불러오기
        Movie movie = movieRepository.findById(2L).get();

        List<Review> list = reviewRepository.findByReview(movie);

        for (Review review : list) {
            System.out.println(review.getMember().getEmail());
        }
    }
}
