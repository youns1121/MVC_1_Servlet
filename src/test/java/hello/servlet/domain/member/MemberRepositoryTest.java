package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach // 테스트 종료후 초기화
    void afterEach() {
        memberRepository.clearStroe();
    }

    @Test
    void save() {

        //given ~주어젔을때
        Member member = new Member("hello", 20);

        //when ~실행했을때
        Member savedMember = memberRepository.save(member);

        //than 결과가 ~
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }


    @Test
    void findAll(){ // 모두 찾기 테스트
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //than
//        Assertions.assertThat(result.size()).isEqualTo(2);
//        Assertions.assertThat(result).contains(member1, member2);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2); // 'result' 안에 member1, memeber2 객체가 있냐

    }
}
