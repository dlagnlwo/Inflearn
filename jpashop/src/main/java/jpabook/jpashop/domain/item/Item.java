package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
   @Inheritance
   상속관계 전략을 지정해야 한다. 전략은 보통 부모클래스에 지정을 한다.
   @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
   strategy 속성은 JOINED, SINGLE_TABLE, TABLE_PER_CLASS 가 있다.
   JOINED : 가장 정규화된 방식
   SINGLE_TABLE : 한 테이블에 컬럼을 다 때려박음.(간단함)
   TABLE_PER_CLASS : 상속된 테이블만 나오는 전략
*/
/*
* @DiscriminatorColumn
* 부모 클래스와 하위 클래스를 같은 테이블에 저장할 때, 어떤 클래스의 객체
* 인지를 구분하기 위한 컬럼을 지정하는 역할을 한다.
* */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // 컬럼을 구분
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    /*비즈니스 로직, setter를 이용해서 재고를 늘리고 줄이는게 아니라
    * 증감메소드로 제어를 하는게 좋다.*/
    // stock(재고) 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    // stock(재고) 삭제
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) { //0보다 작으면 에러가 나게
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
