package com.example.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diary.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    
}
