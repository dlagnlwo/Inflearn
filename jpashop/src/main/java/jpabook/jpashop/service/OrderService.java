package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    /*
      주문을 하려면? 회원, 아이템, 몇개 주문 할지가 필요함.
      memberId : 회원을 선택할 때 필요
      itemId : 상품을 선택할 때 필요
      count : 주문 수량을 선택할 때 필요
    */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성(회원의 주소를 가져와서 배송에 저장)
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); //실제로는 배송지 정보를 입력해야함.

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        /*
          Order테이블에 orderItems필드와 delivery를 CascadeType.All을 해줌.
          CascadeType.All을 해줄 때는 그 테이블이 부모테이블에만 참조할때 사용
          저 옵션을 해주면 저장과 삭제가 같이 실행이 됨.
        */
        orderRepository.save(order); //orderItem, delivery가 같이 저장됨
        return order.getId();

    }

    //주문 취소
    //주문내역에서 CANCEL버튼을 누를 때 id값만 넘어옴
    @Transactional
    public void cancelOrder(Long orderId){
        //일단 Order테이블에 id값을 찾아옴
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }
    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        orderRepository.findAll(orderSearch);
//    }
}
