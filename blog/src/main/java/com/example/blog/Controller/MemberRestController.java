package com.example.blog.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.blog.DTO.MemberDTO;
import com.example.blog.Service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/rest/member")
@RequiredArgsConstructor
@RestController
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/newMember")
    public MemberDTO newMember(@RequestBody MemberDTO memberDTO) {
        return memberService.newMember(memberDTO);
    }

    @DeleteMapping("/removeMember")
    public void removeMember(@RequestBody MemberDTO memberDTO) {
        memberService.removeMember(memberDTO);
    }

}
