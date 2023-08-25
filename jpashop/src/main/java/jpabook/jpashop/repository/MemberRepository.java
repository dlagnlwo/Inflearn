package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository // ComponentScan에 의해 스프링 빈으로 등록됨.
@RequiredArgsConstructor
public class MemberRepository {

//    @Query("select m.name from Member m")
//    @Modifying // @Midifying : 수정, 삭제할때 필요한 어노테이션
    //@Transactional //롤백시키거나, 쿼리에 오류가 있을 때, 실행했던걸 되돌림.
//    public List<Member> findByName(String name);

    //스프링이 EntityManager를 만들어서 여기에 주입해줌.
    private final EntityManager em;

    // 회원저장
    public void save(Member member){
        em.persist(member);
    }

    //id 단건 조회 1
//    @Query("select m from Member m where m.id = :id")
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //id 단건 조회 2
//    public Member findId(Long id) {
//        return em.createQuery("select m from Member m where m.id = :id", Member.class)
//                .setParameter("id", id)
//                .getSingleResult();
//    }

    // 리스트조회
//    @Query("select m from Member m where m.id = :id and m.address = :address and m.name = :name")
    public List<Member> findAll(){
        //em.createQuery(jpql, 반환타입)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //이름으로 검색
//    @Query("select m from Member m where m.name = :name")
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }



}
