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
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // // 회원가입
    // public void register(MemberDTO memberDTO) throws IllegalStateException {

    // // 중복확인
    // validateEmail(memberDTO.getEmail());

    // // dto => entity
    // Member member = Member.builder()
    // .email(memberDTO.getEmail())
    // .fromSocial(memberDTO.isFromSocial())
    // .password(passwordEncoder.encode(memberDTO.getPassword()))
    // .name(memberDTO.getName())
    // .build();
    // member.addMemberRole(MemberRole.USER);
    // memberRepository.save(member);

    // }

    // 중복 확인
    // private void validateEmail(String email) {
    // Optional<Member> member = memberRepository.findById(email);
    // if (member.isPresent()) {
    // // IllegalStateException : : RuntimeException(실행해야 나오는 예외)
    // throw new IllegalStateException("이미 가입된 회원입니다");
    // }
    // }

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

}
