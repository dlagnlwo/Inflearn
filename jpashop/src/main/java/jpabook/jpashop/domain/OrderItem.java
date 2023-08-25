package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
// 기본생성자를 protected타입으로 만들어줌
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격

    private int count; //주문 수량


    //생성 메소드
    //상품(item)을 얼마(orderPrice)에 몇개(count)샀냐
    //할인이나 쿠폰같은게 있기 때문에 item에 있는 price를 사용하는게 아니라
    //OrderItem에 있는 orderPrice를 사용한다.
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 상품을 주문했으니까 그만큼 빠져야 한다.
        item.removeStock(count);
        return orderItem;
    }


    //비즈니스 로직
    //여러 상품 다 캔슬
    //이 cancel()메소드의 뜻은 재고수량을 원복해준다.
    public void cancel() {
        //재고를 다시 주문 수량만큼 늘려줘야한다.
        getItem().addStock(count);
    }

    //조회 로직
    //주문할때 주문 가격과 주문 수량을 곱해야 함.
    //왜? 전체 주문에 대한 가격을 구해야 하니까.
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
