package com.example.jpa.repository.sports;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.sports.SportsMember;

public interface SportsMemberRepository extends JpaRepository<SportsMember, Long> {
    
}
