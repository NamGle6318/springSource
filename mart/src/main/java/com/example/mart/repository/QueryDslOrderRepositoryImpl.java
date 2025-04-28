package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.QItem;
import com.example.mart.entity.QMember;
import com.example.mart.entity.QOrder;
import com.example.mart.entity.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
    };

    @Override
    public List<Member> members() {
        QMember member = QMember.member;
        // select m from Member m where m.name = 'user1' order by m.name desc;
        JPQLQuery<Member> query = from(member);
        query.where(member.name.eq("user1")).orderBy(member.name.desc());
        query.select(member);
        List<Member> list = query.fetch();
        return list;

        // fetch() : 리스트 조회, 데이터 없는 경우 빈 리스트 반환
        // fetchFirst() : limit(1), fetchOne()
        // fetchOne() : 결과가 없으면 null, 둘 이상의 결과가 나오면 NonUniqueException 발생
        // fetchResults() : 페이징 정보 포함, total count 쿼리 추가 실행
        // fetchCount() : count 쿼리로 변경해서 count 수 조회
    }

    @Override
    public List<Item> items() {
        QItem item = QItem.item;

        JPQLQuery<Item> query = from(item);
        // ItemName like '콩나물' and price > 3000
        query.where(item.name.eq("콩나물").and(item.price.gt(3000)));
        query.select(item);

        return query.fetch();
    }

    @Override
    public List<Object[]> joinTest() {
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        QOrderItem orderItem = QOrderItem.orderItem;
        // join 2중문
        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member));
        query.join(orderItem).on(order.eq(orderItem.order));
        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem);
        List<Tuple> result = tuple.fetch();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Object[]> subQueryTest() {
        // 1. Orders에 subQuery count(Order_id) 를 join 한다 (조건 order_id가 동일함)
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        QOrderItem orderItem = QOrderItem.orderItem;

        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member));
        query.join(orderItem).on(order.eq(orderItem.order));
        // query 현 상태 = order, member, orderItem이 3중 join 되었음.

        // query문 select * from order join member on order.member_id = member.member_id
        // join orderItem on order.orderItem_id = orderItem.orderItem.id;

        // 다중행 함수 사용 시 : JPAExpressions 사용하여 subQuery 만들기
        // select 주문건수 (count)
        JPQLQuery<Long> orderCount = JPAExpressions.select(orderItem.order.count())
                .from(orderItem)
                .where(orderItem.order.eq(order))
                .groupBy(orderItem.order);
        // select 주문총금액 (sum)
        JPQLQuery<Integer> orderSum = JPAExpressions.select(orderItem.orderPrice.sum())
                .from(orderItem)
                .where(orderItem.order.eq(order))
                .groupBy(orderItem.order);

        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem, orderCount, orderSum);
        List<Tuple> result = tuple.fetch();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return list;
    }

}
