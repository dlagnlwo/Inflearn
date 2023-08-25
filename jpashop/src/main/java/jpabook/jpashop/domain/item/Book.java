package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B") //저장할때 구분하는 어노테이션
public class Book extends Item{

    private String author; //저자
    private String isbn; //책번호

}
