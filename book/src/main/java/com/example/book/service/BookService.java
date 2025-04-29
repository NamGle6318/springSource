package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public void insert(BookDTO bookDTO) {

    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);

        return bookDTO;

    }

    // Book 엔티티들을 BookDTO로 변환 후 리스트에 담아 반환하는 메소드
    public PageResultDTO<BookDTO> readAll(PageRequestDTO pageRequestDTO) {
        // List<BookDTO> bookDTO = new ArrayList<>();
        // List<Book> books = bookRepository.findAll();

        if (pageRequestDTO.getType() != null && pageRequestDTO.getKeyword() != null) {
        }

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("code").descending());
        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()), pageable);

        List<BookDTO> books = result.get().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return PageResultDTO.<BookDTO>withAll()
                .dtoList(books)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO).build();
    }

    public Long modify(BookDTO bookDTO) {
        Book book = bookRepository.findById(bookDTO.getCode()).get();
        book.setPrice(bookDTO.getPrice());
        bookRepository.save(book);

        return book.getCode();

    }

    public Long create(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        bookRepository.save(book);

        return book.getCode();
    }

    public void delete(Long code) {
        bookRepository.deleteById(code);

    }
}
