package com.example.movie.repository.total;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.QMovie;
import com.example.movie.entity.QMovieImage;
import com.example.movie.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MovieImageReviewRepositoryImpl extends QuerydslRepositorySupport implements MovieImageReviewRepository {

        // movieImage의 inum, path 추출 목적?
        public MovieImageReviewRepositoryImpl() {
                super(MovieImage.class);

        }

        // 영화번호, 영화 이미지, 영화명, 영화의 리뷰 개수, 평균 평점, 생성일을 조회하는 리스트 출력 query문
        @Override
        public Page<Object[]> getTotalList(String type, String keyword, Pageable pageable) {
                QMovie movie = QMovie.movie;
                QMovieImage movieImage = QMovieImage.movieImage;
                QReview review = QReview.review;

                from(movieImage);
                JPQLQuery<MovieImage> query = from(movieImage);
                query.leftJoin(movie).on(movieImage.movie.eq(movie));

                // 리스트의 영화 리뷰개수 subQuery
                JPQLQuery<Long> count = JPAExpressions.select(review.countDistinct()).from(review)
                                .where(review.movie.eq(movieImage.movie));

                JPQLQuery<Double> avg = JPAExpressions.select(review.grade.avg().round()).from(review)
                                .where(review.movie.eq(movieImage.movie));

                JPQLQuery<Tuple> tuple = query
                                .select(movie, movieImage, count, avg)
                                .where(movieImage.inum.in(JPAExpressions.select(movieImage.inum.min())
                                                .from(movieImage)
                                                .groupBy(movieImage.movie)));
                // .orderBy(movie.mno.desc());

                // 검색
                BooleanBuilder builder = new BooleanBuilder();
                BooleanExpression expression = movie.mno.gt(0);
                builder.and(expression);

                // 검색
                BooleanBuilder condition = new BooleanBuilder();

                if (type.contains("title") && keyword != null) {
                        condition.or(movie.title.contains(keyword));

                }
                builder.and(condition);
                tuple.where(builder);

                // Sort 생성
                Sort sort = pageable.getSort();
                sort.stream().forEach(order -> {
                        // import com.querydsl.core.tpyes.Order;
                        Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                        String prop = order.getProperty();
                        PathBuilder<Movie> orderBuilder = new PathBuilder<>(Movie.class, "movie");
                        tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop)));

                });

                tuple.offset(pageable.getOffset());
                tuple.limit(pageable.getPageSize());
                List<Tuple> result = tuple.fetch();
                long totalCnt = tuple.fetchCount();
                return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable,
                                totalCnt);

        }

        // 영화 상세정보
        @Override
        public List<Object[]> getMovieRow(Long mno) {
                log.info("영화 상세정보 요청");

                QMovie movie = QMovie.movie;
                QMovieImage movieImage = QMovieImage.movieImage;
                QReview review = QReview.review;

                from(movieImage);
                JPQLQuery<MovieImage> query = from(movieImage);
                query.leftJoin(movie).on(movieImage.movie.eq(movie));

                // 리스트의 영화 리뷰개수 subQuery
                JPQLQuery<Long> count = JPAExpressions.select(review.countDistinct()).from(review)
                                .where(review.movie.eq(movieImage.movie));

                JPQLQuery<Double> avg = JPAExpressions.select(review.grade.avg().round()).from(review)
                                .where(review.movie.eq(movieImage.movie));

                JPQLQuery<Tuple> tuple = query
                                .select(movie, movieImage, count, avg)
                                .where(movieImage.movie.mno.eq(mno))
                                .orderBy(movieImage.inum.desc());

                List<Tuple> result = tuple.fetch();

                List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

                return list;
        }

}
