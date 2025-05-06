package com.example.diary.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.diary.entity.Diary;

@SpringBootTest
public class DiaryRepositoryTest {
    

    @Autowired
    private DiaryRepository diaryRepository;


    @Test
    public void insertTest() {
        Diary diary = Diary.builder()
        .title("테스트")
        .content("내용")
        .build();
        diaryRepository.save(diary);
    }
    
    @Test
    public void selectTest() {
        System.out.println(diaryRepository.findById(1L).get());
    }

    @Test
    public void updateTest() {
        Diary diary = diaryRepository.findById(1L).get();
        diary.setTitle("수정된 제목");
        diaryRepository.save(diary);

        System.out.println(diaryRepository.findById(1L).get());
    }

    @Test
    public void deleteTest() {
        diaryRepository.deleteById(1L);
    }
}
