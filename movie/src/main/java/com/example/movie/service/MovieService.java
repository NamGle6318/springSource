package com.example.movie.service;

import java.util.Arrays;
import java.util.List;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieImageRepository movieImageRepository;

    // 메인 화면 List 조회
    public PageResultDTO<MovieDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("mno").descending());
        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageable);
        // select(movie, movieImage, count, avg) 순서로 결과 출력하나
        Function<Object[], MovieDTO> function = (en -> entityToDto((Movie) en[0], Arrays.asList((MovieImage) en[1]),
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
}
