package com.example.mvclab.member;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    //서비스(MemberService) => memberRepository.findAll()
    public List<Member> findAll() {
        String sql = """
                SELECT 
                id, name, email, age, password, created_at
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
                SELECT 
                id, name, email, age, password, created_at 
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
        //첫 번째 레코드만 반환
        return result.stream().findFirst();
    }

    //서비스(MemberService) => memberRepository.save(member)
    public Member save(String name, String email, Integer age, String password) {
        String sql = """
                INSERT INTO members(name, email, password, age) 
                VALUES 
                (?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, age);
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return findById(id).orElseThrow();
    }

    public void update(Long id, String name, String email, Integer age) {
        String sql = """
                UPDATE members 
                SET name=?, email=?, age=?  
                WHERE id=?;
                """;

        jdbcTemplate.update(sql, name, email, age, id);
    }

    public void delete(Long id) {
        String sql = """
                DELETE FROM members WHERE id=?;
                """;
        jdbcTemplate.update(sql, id);
    }
}