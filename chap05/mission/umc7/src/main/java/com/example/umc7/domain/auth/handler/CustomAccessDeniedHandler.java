package com.example.umc7.domain.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 필터를 지나 인증 과정을 거친 후 SecurityContext에 인증 객체는 존재하지만,
 * 해당 인증 객체의 권한이 접근하려는 리소스에 접근할 수 있는 권한과 맞지 않으면 AccessDeninedHandler가 호출된다.
 *
 * 정리 : 인증은 되었지만, 인가(권한)이 맞지 않는 경우
 */

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomAccessDeniedHandler(
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("CustomAccessDeniendHandler 동작");

        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}