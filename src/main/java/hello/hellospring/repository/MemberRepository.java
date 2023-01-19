package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);
    //Optional 로 하면 null로 반환되는거 막아줌

    Optional<Member> findByName(String name);

    List<Member> findAll();
}
