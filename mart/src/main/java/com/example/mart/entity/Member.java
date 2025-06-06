package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "MART_MEMBER")
@Entity
public class Member extends BaseEntity {
    // 회원(회원번호(PK), 이름, 우편번호, 주소, 상세주소)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    private String name;

    private String zipCode;
    private String city;
    private String street;

    @OneToMany(mappedBy = "member")
    @Builder.Default // builder 사용시 해당 변수를 처리 안해주는 경우가 있음
    private List<Order> orders = new ArrayList<>();
}
