package com.example.bookapp.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.bookapp.entity.Novel;
import com.example.bookapp.entity.QGenre;
import com.example.bookapp.entity.QGrade;
import com.example.bookapp.entity.QNovel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class SearchNovelRepositoryImpl extends QuerydslRepositorySupport implements SearchNovelRepository {

    public SearchNovelRepositoryImpl() {
        super(Novel.class);

    }

    @Override
    public Object[] getNovelById(Long id) {

        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel);
        query.leftJoin(genre).on(genre.eq(novel.genre));
        query.where(novel.id.eq(id));

        JPQLQuery<Double> ratingAvg = JPAExpressions.select(grade.rating.avg().round()).from(grade)
                .where(grade.novel.eq(novel));
        // .groupBy(grade.novel)

        JPQLQuery<Tuple> tuple = query.select(novel, genre, ratingAvg);
        Tuple result = tuple.fetchFirst();
        return result.toArray();

    }

    @Override
    public Page<Object[]> list(Pageable pageable) {
        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel);
        query.leftJoin(genre).on(genre.eq(novel.genre));

        JPQLQuery<Double> ratingAvg = JPAExpressions.select(grade.rating.avg().round()).from(grade)
                .where(grade.novel.eq(novel));
        // .groupBy(grade.novel)

        JPQLQuery<Tuple> tuple = query.select(novel, genre, ratingAvg);

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = novel.id.gt(0);
        builder.and(expression);
        tuple.where(builder);

        // Sort 생성
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            // import com.querydsl.core.tpyes.Order;
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            String prop = order.getProperty();
            PathBuilder<Novel> orderBuilder = new PathBuilder<>(Novel.class, "novel");
            tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop)));

        });

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        long totalCnt = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, totalCnt);
    }

}
