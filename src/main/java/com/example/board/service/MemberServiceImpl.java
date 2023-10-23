package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    public MemberServiceImpl() {
    }


    public Optional<Member> findOne(String userId) {
        return memberRepository.findByUserid(userId);
    }

    @Override
    public Long join(String userid, String pw) {
        Member member = Member.createUser(userid, pw, passwordEncoder);
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUserid(member.getUserid())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 등록된 아이디입니다. 다른 아이디를 입력해 주세요.");
                });
    }
}
