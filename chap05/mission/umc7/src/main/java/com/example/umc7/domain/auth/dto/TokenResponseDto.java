package com.example.umc7.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "토큰 재발급 응답에 대한 DTO")
public class TokenResponseDto {

    @Schema(description = "재발급된 accessToken", example = "")
    private String accessToken;
    @Schema(description = "기존 refreshToken", example = "")
    private String refreshToken;
}