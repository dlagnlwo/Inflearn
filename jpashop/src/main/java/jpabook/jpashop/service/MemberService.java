package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//접근제한자가 public인 메소드들이 트랜잭션 안에서 다 실행되어야 함.
@Transactional(readOnly = true) //보통 저장 수정하는것보다 조회기능이 더 많으면 클래스 위에다가
                // @Transactional을 걸어놓고 조회가 아닌 기능메소드 위에
                // @Transactional을 걸어줌.
@RequiredArgsConstructor
public class MemberService {

    //필드주입, 생성자주입, set주입보다 @RequiredArgsConstructor를
    //사용하는게 훨씬 더 좋음.
    private final MemberRepository memberRepository;

    //회원 가입
    //읽기가 아닌 곳에 @Transactional(readOnly = true)하면 안됨.
    @Transactional
    public Long join(Member member) throws IllegalAccessException {
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //회원 전체 조회
//    @Transactional(readOnly = true) //조회하는곳에 성능을 최적화
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public List<Member> findByName(String name){
       return memberRepository.findByName(name);
    }

    //회원 단건 조회
//    @Transactional(readOnly = true) //조회하는곳에 성능을 최적화
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    //중복 회원 검증 메소드
    private void validateDuplicateMember(Member member) throws IllegalAccessException {
        // 일단 같은이름이 있는지 확인
        List<Member> findByMembers = memberRepository.findByName(member.getName());
        if(!findByMembers.isEmpty()) {
            throw new IllegalAccessException("이미 존재하는 회원입니다.");
        }

    }
}
