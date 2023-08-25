package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

//값타입은 기본적으로 변경이 되면 안된다. 그렇기 때문에 @Setter를 넣지 않는다.
@Embeddable //어딘가에서 내장이 되어있다는 뜻.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA스펙상 엔티티나 임베디드 타입(@Embeddable)은 자바 기본 생성자를
    // public 또는 protected로 설정해야한다. 보통은 protected를 사용
    protected Address() {
    }

    //@Setter를 사용하는게 아니라 생성할때만 값이 세팅이 되게 해야함.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
