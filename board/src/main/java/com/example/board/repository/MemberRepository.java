package com.example.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

    // email과 fromSocial 여부 확인
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD) // = join, Load할때, roleSet 데이터를 가지고 나옴
    Member findByEmailAndFromSocial(String email, boolean fromSocial);
}
