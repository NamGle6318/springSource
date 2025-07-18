package com.example.elastic2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.elastic2.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
