package com.example.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.security.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // email과 fromSocial 여부 확인
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD) // = join, Load할때, roleSet 데이터를 가지고 나옴
    ClubMember findByEmailAndFromSocial(String email, boolean fromSocial);
}
