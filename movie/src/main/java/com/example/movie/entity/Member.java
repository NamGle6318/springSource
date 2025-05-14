package com.example.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
// import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
// @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "m_member")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

}
