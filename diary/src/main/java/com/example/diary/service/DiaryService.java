package com.example.diary.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diary.entity.Diary;
import com.example.diary.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;

// import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DiaryService{


    // @Autowired
    private final DiaryRepository diaryRepository;


    public void addDiary(String title, String content) {
        // 일기장 작성하기
        Diary diary = Diary.builder().title(title).content(content).build();
        diaryRepository.save(diary);
    }

    public List<Diary> list () {
        List<Diary> list = diaryRepository.findAll();

        return list;
    }

    public void toList(LocalDateTime start, LocalDateTime end) {

    }
}
