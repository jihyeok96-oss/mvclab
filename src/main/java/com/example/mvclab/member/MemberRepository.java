package com.example.mvclab.member;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    //
    public List<Member> findAll() {
        String sql = """
                SELECT id
                     , name
                     , password
                     , email
                     , age
                     , created_at
                  FROM members
                 ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                )
        );
    }

    //서비스(MemberService) => memberRepository.findById(id)
    public Optional<Member> findById(Long id) {
        String sql = """
                SELECT id
                     , name
                     , password
                     , email
                     , age
                     , created_at
                  FROM members
                 WHERE id = ?
                """;

        List<Member> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ), id);

        return result.stream().findFirst();
    }

    //서비스(MemberService) => memberRepository.save(member)
    public Member save(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO members ( name, password, email, age) VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, member.getName());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getEmail());
            ps.setInt(4, member.getAge());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return findById(id).orElseThrow();
    }

    public void update(Member member) {
        String sql = """
                UPDATE members
                   SET name = ?
                     , email = ?
                     , age = ?
                 WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                member.getName(),
                member.getEmail(),
                member.getAge(),
                member.getId()
        );
    }

    public void delete(Long id) {
        String sql = """
                DELETE
                  FROM members
                 WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}