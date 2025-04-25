package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void insertTest() {
        // insert 10개!

        LongStream.rangeClosed(1, 10).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("memo_text" + i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void updateTest() {
        // Memo memo = Memo.builder().mno(2L).memoText("memoText update").build();
        // memoRepository.save(memo);

        Memo memo = memoRepository.findById(1L).get();
        memo.changeMemoText("memoText update");
        memoRepository.save(memo);
    }

    @Test
    public void readTest() {
        Memo memo = memoRepository.findById(1L).get();
        System.out.println(memo);
    }

    @Test
    public void listTest() {
        memoRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void deleteTest() {
        memoRepository.deleteById(10L);

    }

    @Test
    public void queryTest() {
        // System.out.println(memoRepository.findByMnoLessThan(5L));
        // System.out.println(memoRepository.findByMnoLessThanOrderByMnoDesc(5L));
        // System.out.println(memoRepository.findByMemoTextLike("%memo%"));
        // System.out.println(memoRepository.findByMnoBetweenNot(3L, 6L));

    }
}
