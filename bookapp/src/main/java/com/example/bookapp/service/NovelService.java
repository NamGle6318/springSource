package com.example.bookapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookapp.dto.NovelDTO;
import com.example.bookapp.dto.PageRequestDTO;
import com.example.bookapp.dto.PageResultDTO;
import com.example.bookapp.entity.Genre;
import com.example.bookapp.entity.Grade;
import com.example.bookapp.entity.Novel;
import com.example.bookapp.repository.GenreRepository;
import com.example.bookapp.repository.GradeRepository;
import com.example.bookapp.repository.NovelRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class NovelService {

    private final NovelRepository novelRepository;
    private final GenreRepository genreRepository;
    private final GradeRepository gradeRepository;

    public NovelDTO getRow(Long id) {
        Object[] result = novelRepository.getNovelById(id);

        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double rating = (Double) result[2];

        NovelDTO novelDTO = entityToDto(novel, genre, rating);

        return novelDTO;

    }

    public PageResultDTO<NovelDTO> getList(PageRequestDTO pageRequestDTO) {
        // 페이지 -1 => 배열 인덱스 기준 0 시작
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());
        Page<Object[]> result = novelRepository.list(pageable);

        List<NovelDTO> dtoList = result.get().map(arr -> {

            Novel novel = (Novel) arr[0];
            Genre genre = (Genre) arr[1];
            Double rating = (Double) arr[2];

            NovelDTO novelDTO = NovelDTO.builder()
                    .id(novel.getId())
                    .title(novel.getTitle())
                    .author(novel.getAuthor())
                    .publishedDate(novel.getPublishedDate())
                    .available(novel.isAvailable())
                    .gid(genre.getId())
                    .genreName(genre.getName())
                    .rating(rating != null ? rating.intValue() : 0)
                    .build();

            return novelDTO;

        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResultDTO.<NovelDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO)
                .build();
    }

    // 수정하기
    public Long avaUpdate(NovelDTO novelDTO) {
        Novel novel = novelRepository.findById(novelDTO.getId()).get();
        novel.setAvailable(novelDTO.isAvailable());
        novelRepository.save(novel);

        return novel.getId();
    }

    // 삭제하기
    @Transactional
    public Long novelRemove(NovelDTO novelDTO) {
        Novel novel = novelRepository.findById(novelDTO.getId()).get();

        gradeRepository.deleteByNovel(novel);
        novelRepository.deleteById(novelDTO.getId());

        return novelDTO.getId();

    }

    // novel 추가하기
    public Long novelInsert(NovelDTO novelDTO) {
        Novel novel = Novel.builder()
                .title(novelDTO.getTitle())
                .author(novelDTO.getAuthor())
                .available(novelDTO.isAvailable())
                .genre(genreRepository.findById(novelDTO.getGid()).get())
                .build();

        novelRepository.save(novel);

        return novel.getId();

    }

    private NovelDTO entityToDto(Novel novel, Genre genre, Double rating) {

        NovelDTO novelDTO = NovelDTO.builder()
                .id(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .publishedDate(novel.getPublishedDate())
                .available(novel.isAvailable())
                .gid(genre.getId())
                .genreName(genre.getName())
                .rating(rating != null ? rating.intValue() : 0)
                .build();

        return novelDTO;
    }

}
