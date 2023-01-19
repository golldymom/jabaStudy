package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();

    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("goldy");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
        //option + enter 하면 알아서 임포트 스테틱을 할 수 있는 드롭다운(셀렉트박스,셀렉티드)이 나옴
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("godly");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("moon");
        repository.save(member2);

        Member result = repository.findByName("godly").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("godly");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("moon");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }

//tdd 테스트 먼저 만들고 구현하는거

}
