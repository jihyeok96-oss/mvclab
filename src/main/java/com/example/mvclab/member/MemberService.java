package com.example.mvclab.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //Optional<Member> => Member
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(id + "존재하지 않는 회원입니다.")
        );
    }

    //Member save
    public Member save(String name, String email, Integer age, String password) {
        return memberRepository.save(name, email, age, password);
    }

    //Member update
    public void update(Long id, String name, String email, Integer age) {
        findById(id);
        memberRepository.update(id, name, email, age);
    }

    //Member delete
    public void delete(Long id) {
        findById(id);
        memberRepository.delete(id);
    }
}