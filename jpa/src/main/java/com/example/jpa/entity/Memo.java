package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
// @Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

// 번호(mno) 내용(memo_text, length = 200) 생성날짜(created_date) 수정날짜(updated_date)
// mno = 자동증가, PK
// 나머지 칼럼들 = NN
// 내용
@EntityListeners(value = AuditingEntityListener.class)
// @SequenceGenerator(name = "mno_seq", sequenceName = "mno_seq", allocationSize
// = 1)
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mno;

    @Column(length = 200, nullable = false)
    // @Setter
    private String memoText;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void changeMemoText(String memoText) {
        this.memoText = memoText;
    }

}
