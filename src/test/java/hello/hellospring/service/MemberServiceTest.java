package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        //같은 레퍼지토리 사용하게 함 이게 디펜던시 인젝션, di 라고 함
    }


    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();

    }

    @Test
    void join() {
        //void 회원강입  이라고 적어도 됌. 빌드될대 실제 코드에 해당 안됌
        //given
        Member member = new Member();
        member.setName("22222");

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
        IllegalStateException e= assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //터짐

        assertThat(e.getMessage()).isEqualTo("already");

/*        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("already ss");
        }*/

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}