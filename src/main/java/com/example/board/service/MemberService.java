package com.example.board.service;

import com.example.board.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findOne(String userId);

    public Long join(String userid, String pw);
}
