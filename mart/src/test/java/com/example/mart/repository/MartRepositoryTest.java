package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void testMemberInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("User" + i)
                    .city("서울")
                    .street("724-11" + i)
                    .zipCode("1234" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void testItemInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .name("티셔츠" + i)
                    .price(i * 20000)
                    .stockQuantity(i * 5)
                    .build();

            itemRepository.save(item);
        });

    }

    // 주문하다 : Order, OrderItem 값 추가

    @Test
    public void testOrderInsert() {

        // 1. 주문이 발생 -> Order Insert
        Order order = Order.builder()
                .member(memberRepository.findById(3L).get())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        // 2. 해당 Order에 대한 주문상품 추가 -> OrderItem Insert

        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(1L).get())
                .order(order)
                .orderPrice(19600)
                .count(1)
                .build();
        orderItemRepository.save(orderItem);
        OrderItem orderItem2 = OrderItem.builder()
                .item(itemRepository.findById(3L).get())
                .order(order)
                .orderPrice(45000)
                .count(1)
                .build();
        orderItemRepository.save(orderItem2);
    }

    @Transactional
    @Test
    public void testRead1() {
        // 주문 조회(주문번호 이용)
        Order order = orderRepository.findById(1L).get();

        System.out.println(order);

        // 주문자 정보 조회
        System.out.println("주문자 조회" + order.getMember());
    }

    @Transactional
    @Test
    public void testRead2() {
        // 특정 회원의 주문 내역 조회
        // member에서 order를 조회해야 하는 상황
        // order <=> member의 양방향 연결이 되어있는 상태
        // member의 members list를 통해 참조 가능

        Member member = memberRepository.findById(1L).get();
        System.out.println(member.getOrders());
    }

    @Transactional
    @Test
    public void testRead3() {
        // 주문상품의 정보 조회
        OrderItem orderItem = orderItemRepository.findById(1L).get();
        System.out.println("1번 주문상품정보 : " + orderItem);

        // 1번 주문상품의 상품명을 알고 싶음
        // orderItem -> Item
        System.out.println("1번 주문상품의 상품명 : " + orderItem.getItem().getName());

        // 주문 상품을 구매한 사람
        System.out.println("1번 주문상품을 주문한 고객의 정보" + orderItem.getOrder().getMember());

        // 결론 : FK를 통해 다리를 건너 찾아온다.
    }

    @Transactional
    @Test
    public void testRead4() {
        // 주문을 통해 주문 아이템 조회
        // order -> orderItem
        Order order = orderRepository.findById(3L).get();
        order.getOrders().forEach(System.out::println);

    }

    @Test
    public void testDelete1() {
        // 구매 이력이 없는 회원 삭제
        // memberRepository.deleteById(5L);

        // 구매 이력이 있는 회원 삭제 시
        // 우선 순위
        // 1. orderitems에서 1번 유저와 관련된 order와 연결된 orderItem을 제거
        // 2. orders에서 1번 유저와 관련된 내용 삭제
        // 3. member 제거

        // 찾기 메소드 생성
        // List<OrderItem> orderitems = orderItemRepository.findAll();

    }

    @Test
    public void testDelete2() {
        // 3번 주문을 취소해보자
        // 직접 제거하기
        // 1. orderItem 삭제
        orderItemRepository.deleteById(2L);
        // 2. order 삭제
        orderRepository.deleteById(2L);

    }

    @Test
    public void testDelete3() {
        // 3번 주문을 취소해보자 단 주문만 삭제하면 나머지는 알아서 되는 것처럼
        // 1. order 삭제
        orderRepository.deleteById(3L);
        // 부모에 해당하는 order cascade를 걸어 아래 코드를 내부적으로 실행 후 order 실행
        // orderItemRepository.deleteById(2L)
        // orderItemRepository.deleteById(3L)

    }

    @Commit
    @Transactional
    @Test
    public void testDelete4() {
        Order order = orderRepository.findById(4L).get();

        // 주문번호가 4번인 주문상품 조회
        System.out.println(order.getOrders());

        // 첫번째 자식 제거
        order.getOrders().remove(0); // 고아 객체 발생
        orderRepository.save(order); // 고아 객체 컷
        // orphanRemoval = true 로 인해 고아 객체 발생시 삭제가 가능하게 함

        // 결론: 고아객체 삭제를 위해선 부모 객체의 관계에 orphanRemoval = true, save(), @Commit 해야함

    }

    @Test
    public void testOrderInsert2() {
        // Order save시 Order Item도 같이 save
        // 1. 주문이 발생 -> Order Insert
        Order order = Order.builder()
                .member(memberRepository.findById(3L).get())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        // 2. 해당 Order에 대한 주문상품 추가 -> OrderItem Insert

        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(1L).get())
                .order(order)
                .orderPrice(19600)
                .count(1)
                .build();
        // orderItemRepository.save(orderItem);
        order.getOrders().add(orderItem); // orders의 CascadeType.PERSIST임
        orderRepository.save(order); // => order 저장에 따라 orders가 영속화되고, orders의 자식인 orderItem도 같이 영속화됨
    }

    @Test
    public void testDeliveryInsert3() {
        // 5번 Order를 배송해보자!
        Delivery delivery = Delivery.builder()
                .zipCode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        Order order = orderRepository.findById(5L).get();
        order.setDelivery(delivery);
        deliveryRepository.save(delivery);
        orderRepository.save(order);
    }

    @Transactional
    @Test
    public void testRead5() {
        // 배송번호 1번의 정보 조회
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println("1번 배송정보 조회 : " + delivery);

        // 해당 주문의 배송정보 조회
        Order order = orderRepository.findById(5L).get();
        System.out.println("5번 주문내역의 배송정보 조회(order -> delivery) : " + order.getDelivery());

        // 해당 배송의 주문내역 조회
        System.out.println("1번 배송번호의 주문내역 조회(delivery -> order) : " + delivery.getOrder());

        // delivery의 order fk를 접근을 통해 order의 정보를 각각 추출 가능
        System.out.println("order 정보 : " + delivery.getOrder().getId());
        System.out.println("order 정보 : " + delivery.getOrder().getMember());
        System.out.println("order 정보 : " + delivery.getOrder().getOrderDate());
        System.out.println("order 정보 : " + delivery.getOrder().getOrderStatus());
        System.out.println("order 정보 : " + delivery.getOrder().getOrders());

    }

    @Test
    public void testDeliveryInsert4() {
        // 4번 Order를 배송해보자!
        Delivery delivery = Delivery.builder()
                .zipCode("55356")
                .city("경기도어딘가")
                .street("132-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        Order order = orderRepository.findById(1L).get();
        order.setDelivery(delivery);
        // deliveryRepository.save(delivery);
        orderRepository.save(order);
    }

    @Test
    public void deleteTest() {
        orderRepository.deleteById(3L);
    }

    // 1번 상품에 SHIRTS라는 카테고리를 붙히고, Category

    // ----------------------------------------------------------------------------------
    // M : N 테스트해보기
    // ----------------------------------------------------------------------------------

    @Autowired
    public CategoryItemRepository categoryItemRepository;
    @Autowired
    public CategoryRepository categoryRepository;

    @Test
    public void testCategoryItemInsert1() {
        // 카테고리 입력
        Category category = Category.builder()
                .name("가전제품")
                .build();

        Category category2 = Category.builder()
                .name("식품")
                .build();

        Category category3 = Category.builder()
                .name("생활용품")
                .build();

        categoryRepository.save(category);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // 아이템 입력
        Item item = Item.builder()
                .name("TV")
                .price(2500000)
                .stockQuantity(15)
                .build();
        Item item2 = Item.builder()
                .name("콩나물")
                .price(3500)
                .stockQuantity(250)
                .build();
        Item item3 = Item.builder()
                .name("샴푸")
                .price(18700)
                .stockQuantity(38)
                .build();

        itemRepository.save(item);
        itemRepository.save(item2);
        itemRepository.save(item3);

        CategoryItem categoryItem = CategoryItem.builder()
                .category(category)
                .item(item)
                .build();
        CategoryItem categoryItem2 = CategoryItem.builder()
                .category(category2)
                .item(item2)
                .build();
        CategoryItem categoryItem3 = CategoryItem.builder()
                .category(category3)
                .item(item3)
                .build();

        categoryItemRepository.save(categoryItem);
        categoryItemRepository.save(categoryItem2);
        categoryItemRepository.save(categoryItem3);
    }

    @Transactional
    @Test
    public void testReadCategoryItem() {
        // Category Item을 조회해보자
        CategoryItem categoryItem = categoryItemRepository.findById(1L).get();

        System.out.println(categoryItem);
        System.out.println(categoryItem.getCategory());
        System.out.println(categoryItem.getItem());

        Category category = categoryRepository.findById(1L).get();

        category.getCategoryItems().forEach(item -> System.out.println(item.getItem()));
    }

}
