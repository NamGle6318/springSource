package com.example.bookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.bookapp.entity.Grade;
import com.example.bookapp.entity.Novel;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByNovel(Novel novel);

    @Modifying
    @Query("delete from Grade g where g.novel =:novel")
    void deleteByNovel(Novel novel);
}
