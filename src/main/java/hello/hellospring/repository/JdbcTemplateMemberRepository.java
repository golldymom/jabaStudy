package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
/*이건(아마도 jdbcTemplate을 말하는 듯) 인젝션 받을 수 있는거 아님, 그래서 아래 퍼블릭의 괄호에 데이터 소스를 적음
원래 임플리먼트로 옵션 엔터 하면 자동 생성될때 괄호 안에 있던걸 지우고 데이터 소스를 집어 넣음
 */

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //이거가 스프링? 에서 이런 스타일로 쓰는 것을 권장, 생성자(오토 와이어)가 1개 뿐이면 생략해도 됌
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); //인서트라는 구문을 템플릿을 이용해서 간다ㅐㄴ하게 구현함
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); // 테이블 이름과 pk로 쓸 컬럼정의

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName()); //넣어줄 네임 정의 여기까지로 인서트 문 만들어 줌

        Number key = jdbcInsert.executeAndReturnKey(new //키를 받고 이 키를 멤머의 셋 아이디에 넣어서
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    } //도큐먼트 보고 따라하면 됨 친절하게 잘 나와있다고 함.

    @Override
    public Optional<Member> findById(Long id) {
      List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
    List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
//        return new RowMapper<Member>() 여기서 옵션 엔터 치면 람다로 바꿔서 줄을 줄일 수 있음 {
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//                Member member = new Member();
//                member.setId(rs.getLong("id"));
//                member.setName(rs.getString("name"));
//                return member;
//            }
//       아래가 람다로 바꾸는 경우,  }
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };


    }



}
