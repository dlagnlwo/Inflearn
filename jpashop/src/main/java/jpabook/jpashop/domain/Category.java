package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    /*
    * 실무에서는 다대다를 사용하면 안됨.
    * @JoinTable : 다대다관계를 매핑, 중간 테이블을 구현해야하고, 중간테이블은
    * 두 엔티티의 식별자를 외래키로 가진다. 다대다관계이면 보통 저렇게 사용하면됨.
    * joinColumns, inverseJoinColumns : 엔티티의 외래키지정
    * 저렇게 하면 Category와 Item테이블 사이에 category_item을 매핑할 수 있다.
    * */
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    //부모
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    //자식
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 양쪽에 다 들어가야 한다.
    // 연관관계 편의 메소드
    public void addChildCategory(Category child){
        this.child.add(child); //자식에 추가
        child.setParent(this); //부모에서도 추가
    }

}
