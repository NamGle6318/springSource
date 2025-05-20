package com.example.movie.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MemberDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.MemberRole;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public Long createMember(MemberDTO memberDTO) {
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickname(memberDTO.getNickname())
                .memberRole(memberDTO.getMemberRole())
                .build();

        memberRepository.save(member);

        return member.getMid();
    }

    public void updateMember(MemberDTO memberDTO) {

    }

    // member 정보 삭제
    @Transactional
    public void deleteMember(MemberDTO memberDTO) {

        Member member = memberRepository.findByEmail(memberDTO.getEmail());

        if (!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다");
        } else {
            reviewRepository.deleteByMember(member);
            memberRepository.deleteById(member.getMid());
        }
    }
}
