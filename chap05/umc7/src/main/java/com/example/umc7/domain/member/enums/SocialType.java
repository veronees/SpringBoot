package com.example.umc7.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {

    NAVER("네이버"), KAKAO("카카오"), GOOGLE("구글"), APPLE("애플"),
    ;

    private String name;
}