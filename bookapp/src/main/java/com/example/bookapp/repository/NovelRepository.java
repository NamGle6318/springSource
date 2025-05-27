package com.example.bookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookapp.entity.Novel;

public interface NovelRepository extends JpaRepository<Novel, Long>, SearchNovelRepository {

}
