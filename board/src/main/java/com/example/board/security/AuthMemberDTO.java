package com.example.board.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class AuthMemberDTO extends User {

    private String email;
    private String name;
    private String password;
    private boolean fromSocial;

    // 소셜 로그인 미포함
    public AuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

}
