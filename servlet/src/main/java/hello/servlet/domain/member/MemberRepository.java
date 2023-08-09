package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
* */
public class MemberRepository {

    // static으로 딱 하나만 생성되게 함.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // 싱글톤
    private static final MemberRepository INSTANCE = new MemberRepository();

    public static MemberRepository getInstance() {
        return INSTANCE;
    }

    //아무나 생성하지 못하게 기본생성자를 통해서 막음
    private MemberRepository(){
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    // store에 있는 values를 외부에서 건들이지 못함.
    // 즉, store 자체를 보호하기 위해서 저렇게 사용
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    // test 할때 사용하는 코드임. 여기서는 연습이므로 그냥 사용
    // store에 있는 데이터를 모두 날림.
    public void clearStore(){
        store.clear();
    }
}
