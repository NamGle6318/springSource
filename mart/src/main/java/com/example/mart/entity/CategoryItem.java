package com.example.mart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class CategoryItem {
    // id, category_id, item_id

    //
    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CategoryItem = 카테고리된 아이템
    // 하나의 아이템엔 여러개의 카테고리가 존재할 수 있으며
    // 하나의 카테고리엔 여러개의 아이템이 존재할 수 있음

    // 하나의 카테고리에 여러개의 아이템이 있음
    @ManyToOne
    // @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    // 하나의 카테고리에 여러개의 아이템이 있음
    @ManyToOne
    // @JoinColumn(name = "ITEM_ID")
    private Item item;
}
