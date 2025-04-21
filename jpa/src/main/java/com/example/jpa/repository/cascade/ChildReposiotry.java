package com.example.jpa.repository.cascade;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.cascade.Child;

public interface ChildReposiotry extends JpaRepository<Child, Long> {
    
}
