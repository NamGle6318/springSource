package com.example.movie.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.MemberRole;
import com.example.movie.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void register(MemberDTO memberDTO) throws IllegalStateException {

        // 중복확인
        validateEmail(memberDTO.getEmail());

        // dto => entity
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickname(memberDTO.getNickname())
                .memberRole(memberDTO.getMemberRole())
                .build();
        memberRepository.save(member);

    }

    // 중복 확인
    private void validateEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            // IllegalStateException : : RuntimeException(실행해야 나오는 예외)
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }

    // 로그인 처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username test : {}", username);

        Member member = memberRepository.findByEmail(username);

        if (member == null)
            throw new UsernameNotFoundException("이메일 확인");

        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .memberRole(member.getMemberRole())
                .build();

        AuthMemberDTO authMemberDTO = new AuthMemberDTO(memberDTO);

        return authMemberDTO;
    }

    @Transactional
    // 닉네임 변경
    public void updateNickname(MemberDTO memberDTO) {
        // 방법 1. 수정할 거 찾고 -> 변경 -> 세이브
        // Member member = memberRepository.findByEmail(memberDTO.getEmail());
        // member.setNickname(memberDTO.getNickname());
        // memberRepository.save(member);

        // 방법 2. 쿼리문 작성 -> 사용
        memberRepository.updateNickname(memberDTO.getNickname(), memberDTO.getEmail());

    }

    // 비밀번호 변경
    public void updatePassword(PasswordDTO passwordDTO) throws IllegalStateException {
        // 현재 입력된 비밀번호와 일치한지 확인 후 비밀번호 변경

        Member member = memberRepository.findByEmail(passwordDTO.getEmail());

        // 현재 비밀번호 확인

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다");
        } else {
            // 현재 비밀번호를 변경
            member.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            memberRepository.save(member);

        }
    }

}
