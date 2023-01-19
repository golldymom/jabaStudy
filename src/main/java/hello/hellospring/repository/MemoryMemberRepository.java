package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
public class MemoryMemberRepository implements MemberRepository {
//MemberRepository 인터페이스라 함, 찾아볼것
    private static Map<Long, Member> store = new HashMap<>();
    //롱은 아이디, 멤버는 값 위치 고정.
    private static long sequence = 0L;
//sequence는 0,1,2, 생성해 주는 것

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }


    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
//널이 반환될 가능성이 잇으면 옵셔널.오브널어블 하면 클라이언트가 뭔가 할 수 있음.
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();

    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    //루프 돌리기가 list가 편해서 저걸 더 많이 씀


    public void clearStore() {
        store.clear();
    }
}

