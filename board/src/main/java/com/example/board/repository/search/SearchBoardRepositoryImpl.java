package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

// import org.apache.catalina.servlets.DefaultServlet.SortManager.Order;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Sort;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);

    }

    @Override
    public Page<Object[]> list(Pageable pageable) {
        log.info("searchBoard");
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // board join member query
        JPQLQuery<Board> query = from(board);
        query.leftJoin(member)
                .on(board.member.eq(member));

        // 댓글 개수 subQuery
        JPQLQuery<Long> replyCount = JPAExpressions
                .select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount); // 튜플이나 아직까진 query문

        // Sort 생성
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            // import com.querydsl.core.tpyes.Order;
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            String prop = order.getProperty();
            PathBuilder<Board> orderBuilder = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop)));

        });
        // ---------------------------------- 전체 리스트 + Sort 적용

        // 페이지 처리
        tuple.offset(pageable.getOffset()); // page 분리를 offset에서 자동으로 해줌 / 10
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch(); // tuple query문을 실행시켜 하나의 릴레이션으로 생성
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        // Tuple List를 object[] List 로 변환 : Tuple List는 서로 다른 클래스(Board, Member, Long)들이
        // 담겨있음
        // 이들을 통일 시킬려고 가장 조상인 object로 변환함
        log.info("===============================");
        log.info("query : " + query); // board랑 member랑 reply 카운트랑 조인한 query문
        log.info("replyCount : " + replyCount); // subQuery
        log.info("tuple : " + tuple); // tuple을 생성하해 query문의 타입을 변환
        log.info("result : " + result); // 릴레이션
        log.info("list : " + list); // object로 타입 변환
        log.info("===============================");

        Long count = tuple.fetchCount(); // tuple의 전체 개수 구하기

        return new PageImpl<>(list, pageable, count);
    }

    public Object[] getBoardByBno(Long bno) {

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member)
                .on(board.member.eq(member));
        query.where(board.bno.eq(bno));

        JPQLQuery<Long> replyCount = JPAExpressions
                .select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount); // 튜플이나 아직까진 query문

        Tuple row = tuple.fetchFirst();

        return row.toArray();
    }

}
