package com.example.umc7.domain.auth.controller;

import com.example.umc7.apipayload.ApiResponse;
import com.example.umc7.domain.auth.dto.TokenResponseDto;
import com.example.umc7.domain.auth.service.AuthService;
import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.dto.LoginSuccessDto;
import com.example.umc7.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 인가 관련 API")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    private final static String LOGIN_SUCCESS_REDIRECT_URL_LOCAL = "http://localhost:5173";

    @Operation(
        summary = "카카오 로그인 페이지로 리다이렉트 API",
        description = "카카오 로그인 페이지로 리다이렉트하는 API입니다."
    )
    @GetMapping("/oauth2/kakao")
    public void getLoginUrl(HttpServletResponse response) throws IOException {
        String redirectUrl = authService.getRedirectUrl();
        log.info("{}", redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    @Operation(
        summary = "인가 코드를 받아 처리하는 API",
        description = "인가 코드 받아 처리하는 API입니다."
    )
    @GetMapping("/oauth2/kakao/code")
    public void callBack(
        @RequestParam(required = false) String code, HttpServletResponse response)
        throws IOException {
        Map<String, String> memberInfo = authService.getMemberInfo(code);
        LoginSuccessDto loginSuccessDto = memberService.login(memberInfo);

        String redirectUrl =
            LOGIN_SUCCESS_REDIRECT_URL_LOCAL + "?isFirstLogin=" + loginSuccessDto.getIsFirstLogin()
                + "&accessToken=" + loginSuccessDto.getTokenResponseDto().getAccessToken()
                + "&refreshToken=" + loginSuccessDto.getTokenResponseDto().getRefreshToken();

        response.sendRedirect(redirectUrl);
    }

    @Operation(
        summary = "access token 재발급 API",
        description = "access token 재발급하는 API입니다. 해당 API는 헤더에 refreshToken과 함께 요청해야합니다.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "재발급된 acessToken과 기존의 refreshToken을 응답",
                content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
            )
        }
    )
    @GetMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponseDto>> reissue(
        @RequestAttribute("refresh") String refreshToken) {
        return ResponseEntity.ok(ApiResponse.onSuccess(authService.reissue(refreshToken)));
    }

    @Operation(
        summary = "로그아웃 API",
        description = "로그아웃 API입니다. 해당 API는 사용자 인증이 요구됩니다.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "로그아웃 결과 반환",
                content = @Content(schema = @Schema(implementation = String.class))
            )
        }
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@AuthenticationPrincipal Member member) {
        authService.deleteRefreshToken(member);
        return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃 성공"));
    }
}