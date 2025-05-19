package com.example.movie.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/{rno}")
    public ReviewDTO getReview(@PathVariable Long rno) {

        log.info("review 요청 {}", rno);

        return reviewService.getReview(rno);
    }

    @GetMapping("/{mno}/all")
    public List<ReviewDTO> getReviews(@PathVariable Long mno) {

        log.info("review 요청 {}", mno);

        return reviewService.getReplies(mno);
    }

    @PutMapping("/{mno}/{rno}")
    public ReviewDTO putReview(@PathVariable Long rno, @RequestBody ReviewDTO reviewDTO) {
        log.info("수정 요청 {} {} ", rno, reviewDTO);
        ReviewDTO result = reviewService.update(reviewDTO);

        return result;
    }

    @DeleteMapping("/{mno}/{rno}")
    public Long deleteReview(@PathVariable Long mno, @PathVariable Long rno) {
        reviewService.delete(rno);
        return rno;
    }

    @PostMapping("/{mno}")
    public Long postReview(@PathVariable Long mno, @RequestBody ReviewDTO reviewDTO) {
        log.info("댓글 요청 {}", reviewDTO);
        Long resultNum = reviewService.create(reviewDTO);

        return resultNum;
    }

}
