package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    @Query("SELECT SUM(ji.price), AVG(ji.price), MIN(ji.price), MAX(ji.price) , COUNT(ji.id) FROM Item ji")
    List<Object[]> aggreate();

}
