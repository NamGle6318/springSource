package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpa.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
