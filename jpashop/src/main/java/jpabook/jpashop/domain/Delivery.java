package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    //enum클래스는 @Enumerated를 넣어줘야 한다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY(배송준비), COMP(배송중)
}
