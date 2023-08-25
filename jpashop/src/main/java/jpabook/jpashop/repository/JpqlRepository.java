package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpqlRepository extends JpaRepository<Member, Long> {

    /*
    * 컬럼을 넘기려면 메소드 매개변수에 @Param을 이용
    * @Param("name") String name
    * 컬럼을 넘기려면 @Query에 :name 이런식으로 사용
    * 컬럼이 너무 많다면 혹은 귀찮다면 엔티티 자체를 넘김 하지만 충돌이 날 수 있음
    * @Param("mem") Member member
    * 엔티티를 넘기려면 @Query에 :#{#mem.name} 이런식으로 사용
    * */
    //Member엔티티 자체를 매개변수로 받은다음에 사용
    @Transactional
    @Modifying
    @Query("update Member m set m.name = :#{#mem.name}, m.address = :#{mem.address} where m.id = :#{#mem.id}" )
    int update(@Param("mem") Member member);

    @Transactional
    @Modifying
    @Query(value = "update Member m set m.name = :name where m.id = :id")
    int update2(Member member);

    // int 값으로 보통 리턴한다.
    @Transactional
    @Modifying
    @Query(value = "insert into Member(name, address) values(:name, :address)", nativeQuery = true)
    int insert1(@Param("name") String name, @Param("address") String address);

    @Query("select m.id from Member m where :name is not null")
    String findName(@Param("name") String name);

    @Query(value = "select name from Member", nativeQuery = true)
    String findName2();

}
