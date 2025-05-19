package com.example.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;

    // 댓글 조회
    public ReviewDTO getReview(Long rno) {
        Review review = reviewRepository.findById(rno).get();

        ReviewDTO reviewDTO = entityToDTO(review);

        return reviewDTO;
    }

    // 해당 영화의 리뷰들을 조회
    public List<ReviewDTO> getReplies(Long mno) {

        Movie movie = movieRepository.findById(mno).get();

        List<Review> reviews = reviewRepository.findByReview(movie);

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(review -> entityToDTO(review))
                .collect(Collectors.toList());

        return reviewDTOs;
    }

    // 삭제
    public void delete(Long rno) {
        reviewRepository.deleteById(rno);

    }

    public Long create(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);
        // log.info("-------------------------------" + review);
        reviewRepository.save(review);

        return review.getRno();
    }

    // 평점과 내용 수정
    public ReviewDTO update(ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewDTO.getRno()).get();

        review.setText(reviewDTO.getText());
        review.setGrade(reviewDTO.getGrade());
        reviewRepository.save(review);

        ReviewDTO result = entityToDTO(review);

        return result;
    }

    private ReviewDTO entityToDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .rno(review.getRno())
                .grade(review.getGrade())
                .text(review.getText())
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .build();

        return reviewDTO;
    }

    private Review dtoToEntity(ReviewDTO reviewDTO) {
        Member member = memberRepository.findById(reviewDTO.getMid()).get();
        Movie movie = movieRepository.findById(reviewDTO.getMno()).get();
        Review review = Review.builder()
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .member(member)
                .movie(movie)
                .build();

        return review;
    }

}
