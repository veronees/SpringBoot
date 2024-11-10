package com.example.umc7.domain.member.service;

import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member findMember(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }
}