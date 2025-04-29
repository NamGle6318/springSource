package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void listAllTest() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10, "t", "국");
        PageResultDTO<BookDTO> result = bookService.readAll(pageRequestDTO);

        System.out.print("내용 : ");
        System.out.println(result.getDtoList());
        System.out.print("페이지 나누기 정보 : ");
        System.out.println(result.getTotalPage());
        System.out.print("페이지 목록 : ");
        System.out.println(result.getPageNumList());
        System.out.print("다음 페이지 출력 여부 : ");
        System.out.println(result.isNext());
        System.out.print("이전 페이지 출력 여부 : ");
        System.out.println(result.isPrev());
    }

}
