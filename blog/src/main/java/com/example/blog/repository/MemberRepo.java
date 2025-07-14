package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Member;

public interface MemberRepo extends JpaRepository<Member, String> {

}
