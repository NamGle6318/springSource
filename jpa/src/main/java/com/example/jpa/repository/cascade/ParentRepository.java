package com.example.jpa.repository.cascade;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.cascade.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    
}
