package com.example.bookapp.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.bookapp.entity.Genre;
import com.example.bookapp.entity.Grade;
import com.example.bookapp.entity.Member;
import com.example.bookapp.entity.Novel;

@SpringBootTest
public class NovelRepositoryTest {

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void getNovelTest() {
        Object[] result = novelRepository.getNovelById(1L);
        System.out.println(Arrays.toString(result));

        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double ratingAvg = (Double) result[2];

        System.out.println(novel);
        System.out.println(genre);
        System.out.println(ratingAvg);
    }

    @Test
    public void getNovelListTest() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(pageable);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void InsertUserTest() {
        // user 50명 추가

        IntStream.range(1, 50).forEach(i -> {

            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .pw("1111")
                    .nickname("user" + i)
                    .social(false)
                    .build();
            memberRepository.save(member);
        });

        IntStream.range(1, 200).forEach(i -> {
            // grade 200 추가 (1~5점 사이)
            long novelId = (int) (Math.random() * 320) + 1;
            int rating = (int) (Math.random() * 5) + 1;
            int memberNum = (int) (Math.random() * 50) + 1;

            Novel novel = novelRepository.findById(novelId).get();
            Member member = memberRepository.findById("user" + memberNum + "@gmail.com").get();

            Grade grade = Grade.builder()
                    .rating(rating)
                    .novel(novel)
                    .member(member)
                    .build();

            gradeRepository.save(grade);
        });
    }

}
