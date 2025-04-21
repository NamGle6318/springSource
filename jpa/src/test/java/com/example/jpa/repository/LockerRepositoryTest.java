package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.sports.Locker;
import com.example.jpa.entity.sports.SportsMember;
import com.example.jpa.repository.sports.LockerRepository;
import com.example.jpa.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;
    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    // 단방향(SportsMember =>) CRUD 테스트

    @Test
    public void insertTest() {
        // Locker 생성
        IntStream.range(0, 5).forEach(i -> {
            
            Locker locker = Locker.builder()
                    .name("Locker" + i)
                    .build();

            lockerRepository.save(locker);

            // sports member 생성
            SportsMember member = SportsMember.builder()
                    .name("Member" + i)
                    .locker(locker) 
                    .build();

            sportsMemberRepository.save(member);
        });

        // Foreign Key 자동 생성 Locker_Locker_ID
        // 이 값에다가 해당 열을 집어 넣어줘야함
        
        
        
    }

    // 개별조회
    @Test
    public void readTest() {
        System.out.println(lockerRepository.findById(1L).get());
        System.out.println(sportsMemberRepository.findById(1L).get());   
    }

    @Transactional
    @Test
    public void readTest2() {
        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();

        System.out.println(sportsMember);
        System.out.println(sportsMember.getLocker()); // 주소가 연결되어 있어 Locker 1번도 가져올 수 있음
    }
    
    @Test
    public void updateTest() {
        // 3번 회원의 이름을 홍길동으로 변환하기
        SportsMember sportsMember = sportsMemberRepository.findById(3L).get();
        sportsMember.setName("홍길동");

        sportsMemberRepository.save(sportsMember);
    }

    @Test
    public void deleteTest() {
        // 5번 회원을 삭제하기
        sportsMemberRepository.deleteById(5L);
        
    }

    @Test
    public void deleteTest2() {
        // 4번 locker 삭제
        // lockerRepository.deleteById(4L);
        // ORA-02292: 무결성 제약조건이 위배되었습니다- 자식 레코드가 발견되었습니다
        // 4번 라커를 참조중인 키가 있기 때문에 실패
        // 해결 방법 : 4번 라커를 참조중인 4번 회원을 삭제 후 삭제
        //           4번 라커를 참조중인 4번 회원의 라커번호를 옮긴 후 삭제

        // 4번 회원에게 5번 라커를 할당 후 4번 라커를 제거하기
        Locker locker = lockerRepository.findById(5L).get();

        SportsMember sportsMember = sportsMemberRepository.findById(4L).get();
        sportsMember.setLocker(locker);

        sportsMemberRepository.save(sportsMember);

        lockerRepository.deleteById(4L);

        
    }
// ----------------------------------------
// 지금까지 sportsMember에서 locker를 접근했었음.
// 이제 locker에서 sportsMember를 접근해보자

@Test
public void readTest3() {
    Locker locker = lockerRepository.findById(1L).get();

    System.out.println(locker);
    System.out.println(locker.getSportsMember()); // 주소가 연결되어 있어 Locker 1번도 가져올 수 있음
}

    


}
