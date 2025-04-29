package com.example.book.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.book.entity.Book;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Book book = Book.builder()
                    .title("만화" + i + "번")
                    .author("작가")
                    .price(8900)
                    .build();
            bookRepository.save(book);
        });
    }

    @Test
    public void readTest() {
        System.out.println(bookRepository.findById(5L).get());
    }

    @Test
    public void readAllTest() {
        bookRepository.findAll().forEach(book -> System.out.println(book));
    }

    @Test
    public void updateTest() {
        Book book = bookRepository.findById(19L).get();
        book.setPrice(1000000);
        bookRepository.save(book);
    }

    @Test
    public void removeTest() {
        bookRepository.deleteById(20L);
    }
    // list를 페이지화

    @Test
    public void pageTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("code").descending());
        // 페이지당 10개씩 27페이지(내림차순) 생성 후 1페이지 보여주기
        Page<Book> result = bookRepository.findAll(pageable);
        result.getContent().forEach(book -> System.out.println(book));
        System.out.println("전체 행 개수 : " + result.getTotalElements());
        System.out.println("전체 페이지 수 : " + result.getTotalPages());
    }
}
