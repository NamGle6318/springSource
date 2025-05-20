package com.example.movie.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor

@Data
@SuperBuilder
/*
 * @SuperBuilder : 상속관계에서도 안전하게 사용
 * 서브클래스가 이 클래스를 상속할 때 부모 빌드도 함께 빌더로 생성 가능
 * 
 * @Builder.Default : 빌더로 객체 생성시 해당 필드가 포함 안되는 경우 사용할 기본 값 지정
 */

public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String type = "";

    @Builder.Default
    private String keyword = "";

}
