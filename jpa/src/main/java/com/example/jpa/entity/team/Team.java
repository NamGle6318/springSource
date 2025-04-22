package com.example.jpa.entity.team;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 하나의 팀에는 여러명의 회원이 소속된다

@Getter
@Setter
@ToString(exclude = "members")
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Team {
    // id, name(팀명)

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @Builder.Default
    private List<TeamMember> members = new ArrayList<>();
    // Cascade
    // PERSIST
    // REMOVE
    // ALL : 부모의 모든 부분을 자식도 영속?

}
