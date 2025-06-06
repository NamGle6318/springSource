package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.QItem;
import com.example.jpa.entity.Item.ItemStatus;
import com.querydsl.core.BooleanBuilder;

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

    @Test
    public void dslTest() {
        QItem item = QItem.item;
        // where itemNm = 'item2'
        System.out.println(itemRepository.findAll(item.itemNm.eq("item2")));

        // where itemNm like 'item2%'
        System.out.println(itemRepository.findAll(item.itemNm.startsWith("item2")));
        // where itemNm like '%item2'
        System.out.println(itemRepository.findAll(item.itemNm.endsWith("item2")));
        // where itemNm like '%item2%'
        System.out.println(itemRepository.findAll(item.itemNm.contains("item2")));
        // where itemNm = 'item2' and price > 1000
        System.out.println(itemRepository.findAll(item.itemNm.eq("item2").and(item.price.gt(1000))));
        // where itemNm = 'item2' and price >= 1000
        System.out.println(
                itemRepository.findAll(item.itemNm.eq("item2").and(item.price.goe(1000))));

        // where itemNm like '%item2%' or ItemStatus = SOLD_OUT
        System.out.println(
                itemRepository.findAll(item.itemNm.contains("item2").or(item.itemSellStatus.eq(ItemStatus.SOLD_OUT))));

        // where stockNumber >= 30
        System.out.println(itemRepository.findAll(item.stockNumber.goe(30)));
        // wherer price < 35000
        System.out.println(itemRepository.findAll(item.price.lt(35000)));

        // where itemNm = 'item2' and price > 1000
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(item.itemNm.eq("item2"));
        builder.and(item.price.gt(1000));

        System.out.println(itemRepository.findAll(builder));
    }
}
