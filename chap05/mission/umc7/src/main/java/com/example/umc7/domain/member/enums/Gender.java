package com.example.umc7.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MAN("남자"), WOMAN("여자"), OTHER("그 외")
    ;

    private String name;
}