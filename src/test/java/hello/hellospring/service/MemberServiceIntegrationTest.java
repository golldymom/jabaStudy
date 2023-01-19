package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Transactional
//@Commit 이건 테스트 하는 것도 데이터베이스에 저장하는 것 그런데 밑에 assertThrows? 인지 뭔지 쓰려면 트랜젝션온리로만 사용할 수 잇다고 에러 제공함
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    void join() {
        //void 회원강입  이라고 적어도 됌. 빌드될대 실제 코드에 해당 안됌
        //given
        Member member = new Member();
        member.setName("333");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    //정상 처리
    //틀린거
    @Test
    public void 중복_회원_예외() {
        Member member1 = new Member();
        member1.setName("goldy");

        Member member2 = new Member();
        member2.setName("goldy");

        memberService.join(member1);
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//        assertThrows(NullPointerException.class, () -> memberService.join(member2)); //터짐
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //터짐

        assertThat(e.getMessage()).isEqualTo("already");


    }

}