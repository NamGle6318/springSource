package com.example.board.entity;

import java.util.ArrayList;
import java.util.List;

//#region
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//#endregion

@Getter
@Setter
@Builder
@ToString(exclude = { "member", "replys" })
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "BOARD_TBL")
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 게시글에서 댓글 조회용
    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Reply> replys = new ArrayList<>();
}
