package com.example.security;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.entity.ClubMember;
import com.example.security.entity.ClubMemberRole;
import com.example.security.repository.ClubMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class securityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    // password를 암호화 하게 된 후 암호화 된 내용 출력
    public void testEncoder() {

        // 원 비밀번호 : rawpassword
        String password = "1111";

        String encodePassword = passwordEncoder.encode(password);
        // password 원본 : 1111

        System.out.println("원본 password : " + password + "\n" + "암호화된 password : " + encodePassword);
        // 암호화된 password :
        // {bcrypt}$2a$10$pV22sjmXoQCSv1ibjXxy1udOAuQwTa/yzBxtkOjswovwiN.CoY1Qa

        // 비밀번호 원본과 암호화 비교(사용자 입력 = DB내 암호화된 데이터)
        System.out.println("비밀번호 검증 : " + passwordEncoder.matches(password, encodePassword)); // true
        System.out.println("비밀번호 검증 : " + passwordEncoder.matches("2222", encodePassword)); // false

    }

    @Test
    public void insertTest() {
        // 모든 회원은 USER권한 부여
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("test" + i + "@gmail.com")
                    .name("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 8) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if (i > 9) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            clubMemberRepository.save(clubMember);
        });
    }

    // @Transactional 트랜잭션이 필요하나 join구문으로 처리하도록 설정해서 트랜잭션 안써도 됨
    @Test
    public void testRead() {
        System.out.println(clubMemberRepository.findByEmailAndFromSocial("test10@gmail.com", false));

    }
}
