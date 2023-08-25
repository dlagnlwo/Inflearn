package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

//한 회원이 여러개를 주문할 수 있으므로 order(주문)가 '다' Member(회원)이 '일'
@Entity
@Table(name = "orders")
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //연관관계의 주인을 정해주어야 함. 보통 '다'쪽이 외래키를 가지므로 주인
    //@xTOOne(@OneToOne, @ManyToOne)은 기본값이 즉시로딩(EAGER)으로
    //되어있기때문에 지연로딩(fetch = FetchType.LAZY)로 변경해줘야 한다.
    @ManyToOne(fetch = LAZY) //다대일
    @JoinColumn(name = "member_id") //매핑을 무슨 컬럼으로 할거냐
    private Member member;

    //테이블간의 매핑하는 것들은 반드시 지연로딩(LAZY)로 사용해야 한다.
    //@xTOMany(@OneToMany, @ManyToMany는 default가
    //지연로딩(fetch = FetchType.LAZY)로 되어있어서 굳이 써주지 않아도 됨.
    //cascade = CascadeType.All : Order를 저장하면 orderItems에도 저장됨
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //모든 엔티티는 원래 persist를 각각해줘야 하는데, cascade를 사용하면
    //데이터가 같이 저장,삭제됨.(CascadeType.ALL에 한해서)
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태(ORDER(주문), CANCEL(시작))

    //양방향 관계 메소드들
    //연관관계 편의 메소드

    // 양방향 Order <-> Member
    public void setMember(Member member) {
        this.member = member; //Member에 저장되면
        member.getOrders().add(this); //Order에도 저장
    }

    // 양방향 Order <-> OrderItem
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem); //OrderItem에 저장하면
        orderItem.setOrder(this); //Order에도 저장
    }

    // 양방향 Order <-> Delivery
    public void setDelivery(Delivery delivery){
        this.delivery = delivery; //Delivery에 저장하면
        delivery.setOrder(this); //Order에도 저장
    }

    //복잡한 생성은 생성 메소드가 따로 있으면 좋다
    //생성 메소드
    public static Order createOrder(Member member,
                                    Delivery delivery,
                                    OrderItem... orderItems){ //OrderItem은 여러개
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); //상태 : 주문
        order.setOrderDate(LocalDateTime.now()); //주문했을때 현재시각
        return order;
    }

    //비즈니스 로직
    //주문취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){//이미 배송이면
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        //위에 조건이 아니라면 밑 실행
        this.setStatus(OrderStatus.CANCEL); //취소상태로 변경
        //여러상품을 주문하는것이기 때문에 재고를 원복
        for(OrderItem orderItem : this.orderItems){
            orderItem.cancel();
        }
    }

    //조회 로직
    //전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : this.orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    //전체 주문 가격 조회 스트림 활용
//    public int getT(){
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
//    }

}
