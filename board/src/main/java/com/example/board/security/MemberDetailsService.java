package com.example.board.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository MemberRepository;

    // 로그인 처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username test : {}", username);

        Member Member = MemberRepository.findByEmailAndFromSocial(username, false);

        if (Member == null)
            throw new UsernameNotFoundException("이메일 확인");

        AuthMemberDTO AuthMemberDTO = new AuthMemberDTO(Member.getEmail(), Member.getPassword(),
                Member.isFromSocial(),
                Member
                        .getRoleSet()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()));
        AuthMemberDTO.setName(Member.getName());
        AuthMemberDTO.setFromSocial(Member.isFromSocial());

        return AuthMemberDTO;
    }

}
