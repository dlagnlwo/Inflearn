package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //이게 있어야 테스트디비에 데이터가 남으면 안되서 롤백이 됨.
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    EntityManager em;
    @Autowired
    private MessageSource messageSource;

    @Test
    //@Rollback(value = false) //롤백 안한다
    void 회원가입() throws IllegalAccessException {

        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
//        em.flush(); //
        assertThat(member).isEqualTo(memberService.findOne(savedId));
    }

    @Test
    void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("aaa");
        member2.setName("aaa");

        //when
        List<Member> list1 = memberService.findMembers();
        List<Member> list2 = memberService.findMembers();
        //then
        assertThat(list1).isEqualTo(list2);
    }
}