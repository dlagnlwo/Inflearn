package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//한 회원이 여러개를 주문할 수 있으므로 order(주문)가 '다' Member(회원)이 '일'
@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //연관관계의 주인을 정해주어야 함. 보통 '다'쪽이 외래키를 가지므로 주인
    @ManyToOne //다대일
    @JoinColumn(name = "member_id") //매핑을 무슨 컬럼으로 할거냐
    private Member member;

    


}
