package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.QMember;
import com.example.jpa.entity.Member.RoleType;

@SpringBootTest
public class MemberRepsitoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .Name(i + "식이")
                    .age(i + 10)
                    .roleType(RoleType.USER)
                    .description("user")
                    .userId("user" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void queryDslTest() {
        QMember member = QMember.member;
        // where name = "3식이"
        System.out.println(memberRepository.findAll(member.Name.eq("3식이")));

        // where age > 15
        System.out.println(memberRepository.findAll(member.age.gt(15)));

        // where roleType = user
        System.out.println(memberRepository.findAll(member.roleType.eq(RoleType.USER)));

        // where name like '%식이%'
        System.out.println(memberRepository.findAll(member.Name.contains("식이")));

        // 전체 조회 후 no의 내림차순으로 정렬하여 출력
        System.out.println(memberRepository.findAll(Sort.by("no").descending()));

    }
}
