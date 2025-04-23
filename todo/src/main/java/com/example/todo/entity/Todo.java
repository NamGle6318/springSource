package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Todo extends BaseEntity {
    // TODO 테이블 :pk, 내용, 작성일자, 수정일자(미완료 -> 완료), 완료여부(true, false), 중요도(true, false)

    @Id
    @Column(name = "TODO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    // SQL에는 boolean type이 없음 (0 = false | 1 = true)
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0", nullable = false)
    private boolean finished;

    @Column(columnDefinition = "NUMBER(1) DEFAULT 0", nullable = false)
    private boolean importanted;

}
