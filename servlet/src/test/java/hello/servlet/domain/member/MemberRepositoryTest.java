package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    /*
    * MemberRepository는 new로 하면 안됨.
    * 이유는 싱글톤으로 만들었기 때문.
    * */

    // 싱글톤이지만 스프링에서는 이렇게 사용하지 않음.
    // 스프링은 자동으로 싱글톤을 내장하고 있기 때문임.
    MemberRepository memberRepository = MemberRepository.getInstance();

    // @AfterEach : 메소드가 매번 끝날 때 작동하는 어노테이션
    // 이 어노테이션을 사용하는 이유는 메소드가 끝날때마다 데이터를 초기화하기 위함.
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        // given : 이런게 주어졌을 때
        // 변수를 생성(객체를 생성)
        Member member = new Member("홍길동", 20);

        // when : 이걸 실행했을 때
        // 위에서 만든 변수를 save(추가)
        Member savedMember = memberRepository.save(member);

        // then : 결과가 이거여야댐
        // 추가했던 데이터를 조회하면서 그것이 given에서 만든것과 같은지 비교
        // 즉, savedMember의 id와 findMember의 id와 같은지 비교
        Member findMember = memberRepository.findById(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("이순신", 30);
        Member member2 = new Member("홍길동", 25);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        //then
        List<Member> findMembers = memberRepository.findAll();

        // given에서 변수를 2개 생성했으니 Member에는 2개가 저장되어 있어야 함.
        // 그러므로 size()로 Member의 사이즈가 2개인지 확인
        assertThat(findMembers.size()).isEqualTo(2);
        // 사이즈를 확인했으니, 이제는 데이터가 맞는지 확인
        // contains(n1, n2) : 컬렉션(리스트, 세트 등)에 요소들이 포함하고 있는지 확인
        assertThat(findMembers).contains(member1, member2);

    }
}
