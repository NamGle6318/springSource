package com.example.board.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.board.entity.Member;
import com.example.board.entity.MemberRole;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void securityInsertTest() {

        // 모든 회원은 USER권한 부여
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .name("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .build();

            member.addMemberRole(MemberRole.USER);

            if (i > 5) {
                member.addMemberRole(MemberRole.MANAGER);
            }
            if (i > 7) {
                member.addMemberRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }
}
