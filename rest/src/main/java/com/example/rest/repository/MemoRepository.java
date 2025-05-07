package com.example.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rest.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    // // wher mno < 5
    // List<Memo> findByMnoLessThan(Long num);

    // // where mno < 10 order by mno desc
    // List<Memo> findByMnoLessThanOrderByMnoDesc(Long num);

    // // List<Memo> findByMno
    // // where memoText like '%memo%'
    // List<Memo> findByMemoTextLike(String text);

    // List<Memo> findByMnoBetweenNot(Long start, Long end);

}