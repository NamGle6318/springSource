package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.Item.ItemStatus;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertTest() {

        IntStream.rangeClosed(1, 50).forEach(i -> {
            Item item = Item.builder()
                    .itemNm("item" + i)
                    .price(i * 2000)
                    .stockNumber(i + 10)
                    .itemDetail("Item Detail" + i)
                    .itemSellStatus(ItemStatus.SELL)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void aggreateTest() {

        List<Object[]> aggreates = itemRepository.aggreate();

        for (Object[] objects : aggreates) {
            System.out.println(Arrays.toString(objects));
            System.out.println("아이템 가격 합계 : " + objects[0]);
            System.out.println("아이템 가격 평균 : " + objects[1]);
            System.out.println("아이템 가격 최소 : " + objects[2]);
            System.out.println("아이템 가격 최대 : " + objects[3]);
            System.out.println("아이템 수 : " + objects[4]);
            System.out.println("------------------------------------");
        }
    }
}
