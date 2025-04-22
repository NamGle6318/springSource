package com.example.jpa.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원은 단 하나의 팀에 소속된다

@Getter
@Setter
@ToString(exclude = "team")
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class TeamMember {
    // id, name(회원명)

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    // @ManyToOne(fetch = FetchType.LAZY) // 1 : N = Team : User
    @ManyToOne
    @JoinColumn(name = "id") // team_id로 함. -> PK랑 이름 겹쳐서 안됐었음
    private Team team; //
}
