package com.example.relation.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        // 팀(부모) 정보 삽입
        Team team = teamRepository.save(Team.builder().teamName("team3").build());

        // 회원(자식) 정보 삽입
        // teamMemberRepository.save(TeamMember.builder().userName("user1").team(team).build());
    }

    @Test
    public void insertTest2() {
        Team team = teamRepository.findById(1L).get();
        teamMemberRepository.save(TeamMember.builder().userName("user2").team(team).build());
    }

    @Test
    public void readTest1() {
        // 팀 조회
        Team team = teamRepository.findById(1L).get();
        // 멤버 조회
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(team);
        System.out.println(teamMember);
    }

    @Test
    public void readTest2() {

        // 멤버의 팀정보
        TeamMember teamMember = teamMemberRepository.findById(1L).get();
        System.out.println(teamMember);
        // 객체그래프탐색
        System.out.println(teamMember.getTeam());
    }

    @Test
    public void updateTest() {
        // 1번 팀원의 팀 변경 : 2번팀으로 변경
        TeamMember member = teamMemberRepository.findById(1L).get();
        Team team = teamRepository.findById(2L).get();
        member.setTeam(team);

        teamMemberRepository.save(member);
    }

    @Test
    public void deleteTest() {
        // 1번 팀 삭제
        // 무결성 제약조건(C##JAVA.FK9UBP79EI4TV4CRD0R9N7U5I6E)이 위배되었습니다- 자식 레코드가 발견되었습니다
        // teamRepository.deleteById(1L);

        // 해결
        // 1. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 이동하거나 null 값 지정
        // 2. 자식 삭제 후 부모 삭제

        // 2번 팀원의 팀을 2번 팀으로 변경
        TeamMember member = teamMemberRepository.findById(2L).get();
        Team team = teamRepository.findById(2L).get();
        member.setTeam(team);
        teamMemberRepository.save(member);

        // 팀 삭제
        teamRepository.deleteById(1L);
    }

    // -----------------------
    // 양방향 연관관계 : @OneToMany
    // -> 단방향 2개
    // -----------------------

    // 팀 => 회원
    // @Transactional
    @Test
    public void readBiTest1() {

        // 팀 찾기
        Team team = teamRepository.findById(2L).get();
        System.out.println(team);
        // 객체그래프탐색
        team.getMembers().forEach(member -> System.out.println(member));
    }

    @Test
    public void readTest3() {
        Team team = teamRepository.findById(1L).get();
        List<TeamMember> members = teamMemberRepository.findByTeam(team);
        for (TeamMember teamMember : members) {
            System.out.println(teamMember);
        }

    }

    @Test
    public void readTest4() {
        List<Object[]> result = teamMemberRepository.findTeamsMembers(1L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }
}
