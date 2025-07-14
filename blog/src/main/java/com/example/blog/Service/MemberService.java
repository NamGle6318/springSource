package com.example.blog.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blog.DTO.MemberDTO;
import com.example.blog.entity.Board;
import com.example.blog.entity.Member;
import com.example.blog.repository.BoardRepo;
import com.example.blog.repository.MemberRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepo memberRepo;
    private final BoardRepo boardRepo;

    // 회원 생성(임시)
    public MemberDTO newMember(MemberDTO memberDTO) {
        Member member = Member.builder()
                .id(memberDTO.getId())
                .name(memberDTO.getName())
                .build();
        memberRepo.save(member);

        return entityToDto(member);
    }

    // 회원 삭제
    public void removeMember(MemberDTO memberDTO) {
        Member member = memberRepo.findById(memberDTO.getId()).get();
        List<Board> boards = boardRepo.findByAuthor(member);
        boards.forEach(boardRepo::delete);
        memberRepo.deleteById(memberDTO.getId());
    }

    public MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }
}
