package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.jpa.entity.cascade.Child;
import com.example.jpa.entity.cascade.Parent;
import com.example.jpa.repository.cascade.ChildReposiotry;
import com.example.jpa.repository.cascade.ParentRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ParentRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildReposiotry childReposiotry;

    @Test
    public void insertTest() {
        Parent parent = new Parent();
        parent.setName("신형만");
        parentRepository.save(parent);

        Child child1 = new Child();
        child1.setName("신짱구");
        child1.setParent(parent);
        childReposiotry.save(child1);

        Child child2 = new Child();
        child2.setName("신짱아");
        child2.setParent(parent);
        childReposiotry.save(child2);
    }

    @Test
    public void insertTest2() {
        // 부모를 저장하면서 자식도 같이 저장

        Parent parent = new Parent();
        parent.setName("엄마");

        // childs List에 child 생성
        parent.getChilds().add(Child.builder().name("일남이").parent(parent).build());
        parent.getChilds().add(Child.builder().name("이남이").parent(parent).build());
        parent.getChilds().add(Child.builder().name("삼남이").parent(parent).build());

        // childrepository.save를 하지 않고도 일남이~삼남이까지 삽입이 되었다.
        // -> cascade = {CascadeType.PERSIST}
        parentRepository.save(parent);
    }

    @Test
    public void deleteTest() {
        // 부모를 삭제 시 그 자식또한 같이 삭제
        parentRepository.deleteById(42L);
        // CascadeType.REMOVE
    }

    @Commit
    @Transactional
    @Test
    public void deleteTest2() {
        //
        Parent parent = parentRepository.findById(43L).get();

        parent.getChilds().remove(0); // 일남이는 고아 객체 상태
        System.out.println(parent.getChilds());
        parentRepository.save(parent);

        // orphanRemoval = true
    }
}
