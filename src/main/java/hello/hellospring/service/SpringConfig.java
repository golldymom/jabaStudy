package hello.hellospring.service;

import hello.hellospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    //    private DataSource dataSource;
//@Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    } jpa 이전에 사용
    //jpa 이후에 사용
//@Autowired
//    private EntityManager em;
//
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    } springboot jpa 이전에 사용 스프링부트 jpa에 선 아래의  방법을 사용
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Bean

    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceApp timeTraceApp() {
//        return new TimeTraceApp();
//    } //이런건 aop라는걸 확실히 알기 위해 config에 정의해 주는게 좋다.



//@Bean 스프링부트 jpa는 사용 안함
//    public MemberRepository memberRepository() {
//    return new MemoryMemberRepository();
//    return new JdbcMemberRepository(dataSource);
//    return new JdbcTemplateMemberRepository(dataSource);
//    return new JpaMemberRepository(em);
//    }
}
