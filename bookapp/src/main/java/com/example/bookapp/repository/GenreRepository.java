package com.example.bookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookapp.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
