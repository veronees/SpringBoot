package com.example.umc7.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.umc7.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("마이페이지 화면을 위한 멤버 정보를 조회한다")
    void test() {

        Member member = memberService.findMember(4L);
        Assertions.assertThat(member.getName()).isEqualTo("베로");
    }
}