package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.Student.Grade;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest // test용 클래스임을 명시
public class StudentRepositoryTest {

    @Autowired // = new StudentRepository()
    private StudentRepository studentRepository;

    // CRUD Test
    // Repository, Entity 확인
    // C(insert) : save(Entity)
    // U(Update) : save(Entity)
    // Insert와 Update를 구분 = 원본과 변경된 부분이 있다면 update

    @Test // 테스트 메소드임을 명시 지정한 부분만 실행할수 있음.
    // 테스트 메소드는 리턴타입이 void여야 함
    public void insertTest() {
        LongStream.range(1, 11).forEach(i -> {
            Student student = Student.builder()
                    .name("홍길동" + i)
                    .grade(Grade.JUNIOR)
                    .gender("M")
                    .build();
            studentRepository.save(student);
        });
        // insert
    }

    @Test
    public void updateTest() {
        // findByID : select * from 테이블명 where id = 1;
        Student student = studentRepository.findById(1L).get();
        student.setGrade(Grade.SENIOR);

        // update
        studentRepository.save(student);
    }

    @Test
    public void SelectOneTest() {
        // null 판별 가능
        // Optional<Student> student = studentRepository.findById(1L);
        // if (student.isPresent()) {
        // System.out.println(student.get());
        // }
        // exception 출력 바꿀 수 있음
        // studentRepository.findById(3L).orElseThrow(EntityNotFoundException::new);

        Student student = studentRepository.findById(3L).get();
        System.out.println(student);

    }

    // 전체 조회
    @Test
    public void selectAllTest() {
        // List<Student> list = studentRepository.findAll();

        // for (Student student : list) {
        // System.out.println(student);
        // }

        studentRepository.findAll().forEach(System.out::println);
    }

    // 삭제하기
    @Test
    public void deleteTest() {
        // Student student = studentRepository.findById(5L).get();
        // studentRepository.delete(student);

        studentRepository.deleteById(10L);
    }
}
