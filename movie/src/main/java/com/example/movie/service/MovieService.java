package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.MovieImageDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MovieService {

        private final MovieImageRepository movieImageRepository;
        private final MovieRepository movieRepository;
        private final ReviewRepository reviewRepository;

        // 영화 저장

        @Transactional
        public Long createMovie(MovieDTO movieDTO) {
                log.info("영화삽입 {}", movieDTO);
                // dto => entity
                Map<String, Object> resultMap = dtoToEntity(movieDTO);

                Movie movie = (Movie) resultMap.get("movie");
                List<MovieImage> movieImages = (List<MovieImage>) resultMap.get("movieImages");

                movieRepository.save(movie);

                for (MovieImage movieImage : movieImages) {
                        movieImageRepository.save(movieImage);
                }
                return movie.getMno();
        }

        // 영화 상세정보 조회

        public MovieDTO getRow(Long mno) {
                // (.)movie, (.)movieImage, (Long)cnt, (Double)avg
                List<Object[]> result = movieImageRepository.getMovieRow(mno);
                List<MovieImage> movieImages = new ArrayList<>();

                // 반복되는 정보인 movie, cnt, avg만 따로 저장
                Object[] row = result.get(0);
                Movie movie = (Movie) row[0];
                Long reviewCnt = (Long) row[2];
                Double avg = (Double) row[3];

                // movieImage 여러개 저장
                for (Object[] objects : result) {

                        movieImages.add((MovieImage) objects[1]);
                }

                MovieDTO movieDTO = entityToDto(movie, movieImages, reviewCnt, avg);
                return movieDTO;
        }

        // 메인 화면 List 조회
        public PageResultDTO<MovieDTO> getList(PageRequestDTO pageRequestDTO) {

                Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                                Sort.by("mno").descending());
                Page<Object[]> result = movieImageRepository.getTotalList(pageRequestDTO.getType(),
                                pageRequestDTO.getKeyword(), pageable);
                // select(movie, movieImage, count, avg) 순서로 결과 출력하나
                Function<Object[], MovieDTO> function = (en -> entityToDto((Movie) en[0],
                                Arrays.asList((MovieImage) en[1]),
                                (Long) en[2],
                                (Double) en[3]));

                List<MovieDTO> dtoList = result.stream().map(function).collect(Collectors.toList());
                Long totalCount = result.getTotalElements();

                PageResultDTO<MovieDTO> pageResultDTO = PageResultDTO.<MovieDTO>withAll()
                                .dtoList(dtoList)
                                .totalCount(totalCount)
                                .pageRequestDTO(pageRequestDTO)
                                .build();
                return pageResultDTO;

        }

        // movie 삭제
        @Transactional
        public void deleteRow(Long mno) {

                // 제거할 영화 탐색
                Movie movie = movieRepository.findById(mno).get();

                // 자식 우선 제거
                reviewRepository.deleteByMovie(movie);
                movieImageRepository.deleteByMovie(movie);

                // 부모 제거
                movieRepository.delete(movie);

        }

        // entity -> DTO 클래스로 변경
        public MovieDTO entityToDto(Movie movie, List<MovieImage> movieImages, Long count, Double avg) {
                MovieDTO movieDTO = MovieDTO.builder()
                                .mno(movie.getMno())
                                .title(movie.getTitle())
                                .createdDate(movie.getCreatedDate())
                                .avg(avg)
                                .reviewCnt(count)
                                .build();

                // 이미지 정보 담기
                List<MovieImageDTO> mImeageDTOs = movieImages.stream().map(movieImage -> {
                        return MovieImageDTO.builder()
                                        .inum(movieImage.getInum())
                                        .uuid(movieImage.getUuid())
                                        .imgName(movieImage.getImgName())
                                        .path(movieImage.getPath())
                                        .build();
                }).collect(Collectors.toList());

                movieDTO.setMovieImages(mImeageDTOs);
                movieDTO.setAvg(avg != null ? avg : 0.0);
                movieDTO.setReviewCnt(count);

                return movieDTO;
        }

        public Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
                Map<String, Object> resultMap = new HashMap<>();

                Movie movie = Movie.builder()
                                .mno(movieDTO.getMno())
                                .title(movieDTO.getTitle())
                                .build();

                resultMap.put("movie", movie);

                List<MovieImageDTO> movieImageDTOs = movieDTO.getMovieImages();
                if (movieDTO.getMovieImages() != null && movieDTO.getMovieImages().size() > 0) {
                        List<MovieImage> movieImages = movieImageDTOs.stream().map(image -> {
                                MovieImage movieImage = MovieImage.builder()
                                                .uuid(image.getUuid())
                                                .path(image.getPath())
                                                .imgName(image.getImgName())
                                                .movie(movie)
                                                .build();
                                return movieImage;
                        }).collect(Collectors.toList());

                        resultMap.put("movieImages", movieImages);

                }

                return resultMap;
        }
}
