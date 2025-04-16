package com.example.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity // == db table
@Table(name = "studenttbl")
@SequenceGenerator(name = "student_seq_gen", sequenceName = "student_seq", allocationSize = 1)
public class Student {
    // @GeneratedValue // create sequence studenttbl_seq start with 1 increment by
    // 50
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")
    private long id;

    private String name;

}

// create table student (
// id number(19,0) not null primary key,
// name varchar2(255 char),
// )