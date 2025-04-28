package com.example.relation.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    // Team을 기준으로 멤버 찾기
    List<TeamMember> findByTeam(Team team);

    // Team을 기준으로 멤버 찾기 + Team 정보도 찾기

    @Query("select m, t from TeamMember m left join m.team t where t.id = :id")
    List<Object[]> findTeamsMembers(Long id);
}
