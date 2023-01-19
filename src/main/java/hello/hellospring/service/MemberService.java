package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public class MemberService {
    //test도 같은 레파지토리에서 하고 싶다. 지금처럼 뉴를 선언해서 새로운 객체에 하는것이 아니라.
    //기존 뉴 선언문
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //테스트에서도 같은 레퍼지토리를 사용하고 싶다.
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    회원가입
    public Long join(Member member) {
//        같은 이름이 있는 중복 회원 가입 안됌


            validateDuplicateMember(member); //중복회원 검증
            memberRepository.save(member);
            return member.getId();



    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("already");
                });
    }

    //전체회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }



}
