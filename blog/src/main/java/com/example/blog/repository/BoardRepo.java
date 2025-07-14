package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Board;
import com.example.blog.entity.Member;

public interface BoardRepo extends JpaRepository<Board, Long> {

    List<Board> findByAuthor(Member author);
}
