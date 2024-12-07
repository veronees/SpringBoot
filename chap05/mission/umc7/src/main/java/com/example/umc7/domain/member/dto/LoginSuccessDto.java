package com.example.umc7.domain.member.dto;

import com.example.umc7.domain.auth.dto.TokenResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({"isFirstLogin", "tokenResponseDto"})
public class LoginSuccessDto {

    @JsonProperty("isFirstLogin")
    private Boolean isFirstLogin;
    private TokenResponseDto tokenResponseDto;
}