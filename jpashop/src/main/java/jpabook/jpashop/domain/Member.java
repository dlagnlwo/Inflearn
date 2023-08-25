package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@Table어노테이션이 없으면 디폴드 값으로 클래스 이름을 대문자에서 소문자로 바꿈
//@Column 어노테이션도 마찬가지. 소문자면 언더바가 붙어버림.
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

    //@OneToMany(mappedBy = "member") : 일대다에서 '다'쪽의
    //order테이블의 member필드에 의해 매핑된것이다. 라는 뜻.(거울이라고 생각)
    //즉, fk를 가지고 있는 테이블의 반대테이블에 mappedBy를 사용한다.
    //컬렉션은 필드에서 바로 초기화하는 것이 안전하다.
    //컬렉션은 가급적이면 바꾸지 마라.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
