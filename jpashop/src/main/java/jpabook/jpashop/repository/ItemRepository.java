package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository{

    //@Query : Repository를 인터페이스로 만들었을때 사용

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null) {
            em.persist(item); //신규등록
        }else {
            em.merge(item); //업데이트라고 이해하면 됨.
        }
    }

    //id 단건조회
//    @Query("select i.id from Item i")
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    //파라미터를 갖고오면 :name 해줘야함.
    public Item findByName(String name){
        return em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name) //set으로 저장
                .getSingleResult();
    }

//    @Query(nativeQuery = true, value = "select name from Item")
//    public Item findByName2(String name){
//        return em.find(Item.class, name);
//    }
//
//    public Item findByName3(String name) {
//        return em.find(Item.class, name);
//    }

    //이거는 생성자가 따로 있어야 함.
//    public List<Item> findAll2(){
//        return em.createQuery("select NEW jpabook.jpashop.domain.item.Item(i.name) from Item i", Item.class)
//                .getResultList();
//    }


    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }



}
