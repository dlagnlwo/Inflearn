package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Test
    void 상품주문(){
        //given 회원과 상품(책)을 저장
        Member member = createMember();

        Book book = createBook("회원1", 10000, 10);
        //when - 주문
        int orderCount = 2; //2권 주문
        Long order = orderService.order(member.getId(), book.getId(), orderCount);

        //then - 검증
        Order getOrder = orderRepository.findOne(order);
        //assertEquals(기댓값, 실제값, 메시지)
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문한 수량만큼 재고가 줄어야 한다.");
    }


    @Test
    void 상품주문_재고수량초과(){
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        //재고수량을 일부러 초과되게 저장
        int orderCount = 11;

        //when
//        orderService.order(member.getId(), item.getId(), orderCount);
        //then
        //JUnit4의 fail()부분을 밑 코드처럼
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    void 주문취소(){
        //given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //주문
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        //상태가 CANCEL이냐?
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소 시 상태는 CANCEL이다.");
        //취소된 상품은 재고에 그대로 돌려내야한다.
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price); //가격
        book.setStockQuantity(stockQuantity); //재고수량
        em.persist(book);
        return book;
    }

}