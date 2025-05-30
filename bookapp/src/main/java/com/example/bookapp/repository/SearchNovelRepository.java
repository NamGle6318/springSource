package com.example.bookapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookapp.dto.PageRequestDTO;

public interface SearchNovelRepository {
    // 하나 조회
    Object[] getNovelById(Long id);

    // 페이지 나누기 + 조회 + 검새

    Page<Object[]> list(Pageable pageable, PageRequestDTO pageRequestDTO);
}
