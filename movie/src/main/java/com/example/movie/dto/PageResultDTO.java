package com.example.movie.dto;

import lombok.Data;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data

public class PageResultDTO<E> {

    // 화면에 보여줄 목록
    private List<E> dtoList;

    // 페이지번호 목록
    // 전체개수 / 페이지당 보여줄 개수 | 필요 페이지 개수 개수 102개 = 필요 페이지 11개
    private List<Integer> pageNumList;

    // page, size 가지고 있음
    private PageRequestDTO pageRequestDTO;

    // 이전, 다음 보여주기 여부
    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    // 해당 생성자에 대한 빌더 어노테이션 적용 및 메서드명 변경 builder() -> withAll()
    @Builder(builderMethodName = "withAll")
    public PageResultDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount;

        // 화면에 페이지 나누기 보여주기 위해 계산
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10; // 마지막 페이지 목록
        int start = end - 9; // 시작 페이지 목록
        int last = (int) (Math.ceil(totalCount / (double) pageRequestDTO.getSize())); // 마지막 페이지 재검토

        end = end > last ? last : end;

        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();

        // 현재 페이지 목록 생성하여 List[integer] 타입으로 반환
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        if (prev) {
            this.prevPage = start - 1;
        }
        if (next) {
            this.nextPage = end + 1;
        }

        totalPage = this.pageNumList.size();
        // 사용자가 요청한 페이지
        this.current = pageRequestDTO.getPage();
    }
}
