package com.example.umc7.domain.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 필터를 지나 인증 과정을 거친 후 SecurityContext에 인증 객체가 없는 경우,
 * 리소스에 접근하려할 때 AuthenticationEntryPoint를 호출한다.
 */

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomAuthenticationEntryPoint(
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        log.info("CustomAuthenticationEntryPoint 동작");

        // 토큰에 문제 있거나, 토큰에 담긴 clientId의 유저가 우리 db에 없는 경우
        if (request.getAttribute("exception") != null) {
            handlerExceptionResolver.resolveException(request, response, null,
                (Exception) request.getAttribute("exception"));
        } else { // 토큰을 담지 않아서 jwtFilter에서 토큰 검증 로직 없이 다음 필터로 넘어간 경우
            handlerExceptionResolver.resolveException(request, response, null, authException);
        }
    }
}