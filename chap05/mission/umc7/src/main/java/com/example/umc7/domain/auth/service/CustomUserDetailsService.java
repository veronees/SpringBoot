package com.example.umc7.domain.auth.service;

import com.example.umc7.domain.auth.CustomUserDetails;
import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {

        // db에 jwt 토큰에서 꺼낸 clientId의 유저가 없으면 예외
        Member member = memberRepository.findByClientId(clientId).orElseThrow(
            () -> new UsernameNotFoundException("User not found with clientId: " + clientId));

        // 존재하면 UserDetails 객체 생성해서 반환 -> SecurityContextHolder에 저장할 것임.
        log.info("{}", member);
        return new CustomUserDetails(member);
    }
}