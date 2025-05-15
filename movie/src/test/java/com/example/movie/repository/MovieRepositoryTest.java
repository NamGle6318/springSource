package com.example.movie.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movie.config.SecurityConfig;
import com.example.movie.entity.Member;
import com.example.movie.entity.MemberRole;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 영화 삽임
    @Test
    public void insertMovieTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("재밋는 영화" + i + "편")
                    .build();

            movieRepository.save(movie);
            // 임의의 이미지
            int count = (int) (Math.random() * 5) + 1;
            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .ord(j)
                        .imgName("test" + j + ".jpg")
                        .movie(movie)
                        .build();

                movie.addImage(movieImage);
                movieImageRepository.save(movieImage);
            }
        });
    }

    // 멤버 삽입

    @Test
    public void insertMemberTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .memberRole(MemberRole.MEMBER)
                    .nickname("viewer" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void reviewInsertTest() {
        // 리뷰 200개 / 영화 100개 / 멤버 무작위 추출

        IntStream.rangeClosed(1, 200).forEach(i -> {
            long randomMember = (int) (Math.random() * 20 + 1);
            long randomMovie = (int) (Math.random() * 100 + 1);

            Review review = Review.builder()
                    .grade((int) (Math.random() * 5 + 1))
                    .text("흥미로워요!")
                    .member(memberRepository.findById(randomMember).get())
                    .movie(movieRepository.findById(randomMovie).get())
                    .build();

            reviewRepository.save(review);
        });
    }

    // 1페이지 출력
    @Test
    public void listTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageable);
        for (Object[] objects : result) {
            // select(movie, movieImage, count, avg) 순서로 결과 출력하나
            // **movie, movieImage의 타입은 해당 클래스가 아닌 Object[]이기 때문에 casting이 필요함**
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void readTest() {
        List<Object[]> result = movieImageRepository.getMovieRow(2L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
        System.out.println("------------------");
        System.out.println(Arrays.toString(result.get(0)));
    }

}
