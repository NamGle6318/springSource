package com.example.movie.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class AuthMemberDTO extends User {

    private MemberDTO memberDTO;

    public AuthMemberDTO(MemberDTO memberDTO) {

        super(memberDTO.getEmail(), memberDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDTO.getMemberRole())));

        this.memberDTO = memberDTO;

    }

}
