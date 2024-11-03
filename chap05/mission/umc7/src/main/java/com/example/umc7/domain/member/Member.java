package com.example.umc7.domain.member;

import com.example.umc7.domain.member.enums.Gender;
import com.example.umc7.domain.member.enums.SocialType;
import com.example.umc7.global.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Member extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    private String address;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String email;

    private Integer point;
}