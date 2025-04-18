package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.team.Team;
import com.example.jpa.entity.team.TeamMember;
import com.example.jpa.repository.team.TeamMemberRepository;
import com.example.jpa.repository.team.TeamRepository;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        // Team 정보 삽입
        Team team = teamRepository.save(Team.builder().teamName("gravitiy").build());

        // 회원 정보 삽입
        // * 외래키로 team_id를 추가하는 것이 아닌 해당 열을 넣는다.
        TeamMember teamMember = teamMemberRepository.save(TeamMember.builder().userName("도우너").team(team).build());
    }

    @Test
    public void insertTest2() {
        // Team 정보 삽입
        Team team = teamRepository.findById(1L).get();

        // 회원 정보 삽입
        // * 외래키로 team_id를 추가하는 것이 아닌 해당 열을 넣는다.
        // TeamMember teamMember =
        teamMemberRepository.save(TeamMember.builder().userName("마이클").team(team).build());
    }
}
