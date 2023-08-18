package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
    * 회원엔티티
*/

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    //@Embedded 어노테이션은 Address클래스에만 있어도 됨.
    @Embedded //내장타입을 포함했다는 뜻.
    private Address address;

    //@OneToMany : 일대다관계
    //@OneToMany(mappedBy = "member") : 일대다에서 '다'쪽의
    //order테이블의 member필드에 의해 매핑된것이다. 라는 뜻.(거울이라고 생각)
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
