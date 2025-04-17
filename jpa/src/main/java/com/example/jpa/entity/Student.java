package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)

@Getter
@Setter
@lombok.Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity // == db table
@Table(name = "studenttbl")
@SequenceGenerator(name = "student_seq_gen", sequenceName = "studenttbl_seq", allocationSize = 1)
public class Student {
    // @GeneratedValue // create sequence studenttbl_seq start with 1 increment by
    // 50
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")
    private long id;

    // @Column(name = "sname", length = 100, nullable = false, unique = true)
    // @Column(name = "sname", columnDefinition = "varchar2(20) not null unique")
    @Column(length = 20, nullable = false)
    private String name;

    // @Column(columnDefinition = "number(8, 0)")
    // private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Grade grade;

    @Column(columnDefinition = "varchar2(1) CONSTRAINT chk_gender CHECK(gender IN('M','F'))")
    private String gender;

    @CreationTimestamp
    private LocalDateTime cDateTime;
    @UpdateTimestamp
    private LocalDateTime uDateTime;

    @CreatedDate
    private LocalDateTime cDateTime2;
    @LastModifiedDate
    private LocalDateTime uDateTime2;

    // enum 정의
    // enum[FRESHMAN, SOPHOMORE, JUNIOR, SENIOR]
    public enum Grade {
        FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    }
}

// create table student (
// id number(19,0) not null primary key,
// name varchar2(255 char),
// )

// long = number(19, 0)
// int = number(10, 0)
// string = varchar2(20 char)
// LocalDateTime = timestamp(6)