package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //기본 생성자
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    // 데이터 타입을 int가 아닌 Integer를 사용하는 이유는
    // 값이 null일수도 있기 때문이다. Integer는 값이 null이어도
    // 0으로 출력 되게 한다.
    private Integer price;
    private Integer quantity;


    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
