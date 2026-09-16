package com.example.mvclab.member;

import com.example.mvclab.exception.MemberNotFoundException;
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

    public Member findById(Long id) throws MemberNotFoundException {
        return memberRepository.findById(id).orElseThrow(
                () -> new MemberNotFoundException(id)
        );
    }

    public Member save(String name, String password, String email, Integer age) {
        Member member = Member.builder()
                .name(name)
                .password(password)
                .email(email)
                .age(age)
                .build();

        return memberRepository.save(member);
    }

    public void update(Long id, String name, String email, Integer age) {
        Member member = Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .age(age)
                .build();

        memberRepository.update(member);
    }

    public void delete(Long id) {
//        findById(id);
        memberRepository.delete(id);
    }
}