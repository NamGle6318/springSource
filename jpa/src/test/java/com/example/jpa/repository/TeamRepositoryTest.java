package com.example.jpa.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.team.Team;
import com.example.jpa.entity.team.TeamMember;
import com.example.jpa.repository.team.TeamMemberRepository;
import com.example.jpa.repository.team.TeamRepository;

import jakarta.transaction.Transactional;

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

    // Lazy 익셉션을 발생시켜보자!
    // Member과 Team의 관계 = N:1(LAZY로 만듦)
    // 1. TEAM 조회시 해당 TEAM에 관계되어 있는 Member 조회
    @Transactional
    @Test
    public void readTest1() {
        // Team team
        Team team = teamRepository.findById(1L).get();
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(team);
        System.out.println(teamMember);
    }

    @Transactional
    @Test
    public void selectTest() {
        // "gravitiy"에 속해있는  Member 조회
        Team team = teamRepository.findById(1L).get();
        
        List<TeamMember> members = team.getMembers();

        System.out.println(members);
        
        // 1. TeamMember중 FK가 1에 해당하는 인원들을 List에 담아 List를 출력
        
        
        // members.add(teamMemberRepository.findBy;
        // System.out.println(member);
    }


}
