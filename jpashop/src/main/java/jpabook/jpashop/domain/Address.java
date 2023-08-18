package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //어딘가에서 내장이 되어있다는 뜻.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
