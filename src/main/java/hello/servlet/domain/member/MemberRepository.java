package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }

    private MemberRepository(){
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) { // 회원 ID로 찾기
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values()); // store에 있는 모든 값을 꺼내 새로운 ArrayList에 넣음, store에 있는 values를  보호하기 위함
    }

    public void clearStroe(){
        store.clear(); // 테스트용, 스토어 날려버리기
    }
}
