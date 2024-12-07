package com.example.umc7.domain.auth.jwt;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.auth.CustomUserDetails;
import com.example.umc7.domain.auth.enums.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // 해당 uri는 가장 첫 if문에서 다음 필터로 넘긴다.
    // permitAll을 하더라도 필터는 거쳐간다.
    // permitAll의 의미는 필터를 거치지 않는 것이 아니라,
    // 필터를 모두 거치고 나서 SecurityContext에 Authentication이 없더라도 인가해주는 것임.
    private static final List<String> EXCLUDE_URLS = List.of(
        "/favicon.ico", "/oauth2/kakao", "/oauth2/kakao/code", "/ws"
    );

    private static final List<String> REFRESH_URLS = List.of("/reissue");

    public static final String HEADER_ATTRIBUTE_NAME_AUTHORIZATION = "Authorization";
    public final static String TOKEN_PREFIX = "Bearer ";
    private static final String REQUEST_ATTRIBUTE_NAME_REFRESH = "refresh";


    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        log.info("Processing request: {}", request.getRequestURI());
        if (shouldExclude(request)) {
            filterChain.doFilter(request, response); // 다음 필터로 진행
            return;
        }

        // 요청 헤더에서 Authorization 정보를 가져옴
        String authHeader = request.getHeader(HEADER_ATTRIBUTE_NAME_AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response); // Authorization 헤더가 없거나 잘못된 경우, 다음 필터로 진행
            return;
        }

        // Authorization 헤더에서 JWT를 추출
        String jwt = authHeader.substring(TOKEN_PREFIX.length());

        String clientId = "";
        UserDetails userDetails;
        try {
            // JWT를 검증
            jwtUtil.validateToken(jwt);

            // JWT에서 사용자 이름을 추출하고, 사용자 세부 정보를 로드
            clientId =  jwtUtil.extractClientId(jwt);

            // clientId로 db에서 우리 서비스의 사용자인지 확인 후 UserDetails 생성 반환
            userDetails = userDetailsService.loadUserByUsername(clientId);

        } catch (Exception e) {
            request.setAttribute("exception", e);
            filterChain.doFilter(request, response);
            return;
        }

        // refresh token이지만, 토큰 재발급 요청 엔드포인트가 아닌 경우 예외 처리
        if (jwtUtil.extractTokenType(jwt).equals(TokenType.REFRESH.toString())) {
            if (isNotAllowRefresh(request)) {
                log.info("리프레시 토큰이지만, 엔드포인트가 reissue가 아닌 경우");
                request.setAttribute("exception", new GeneralException(ErrorStatus.TOKEN_MISMATCH));
            }
            // reissue 엔드포인트라면 Request Attributes에 refresh token 값 추가. 컨트롤러에서 꺼내서 사용
            request.setAttribute(REQUEST_ATTRIBUTE_NAME_REFRESH, jwt);
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        // 사용자 정보와 권한을 설정하고 SecurityContext에 인증 정보를 저장
        // Authentication 객체에 UserDeatils가 아닌 member 객체를 넣는다.
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            customUserDetails.getMember(),
            null,
            userDetails.getAuthorities()
        );

        // Authentication에 현재 요청 정보를 저장해준다. (상세 설정? 안해도 되긴 할듯)
        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // SecurityContext에 Authentication 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    private boolean shouldExclude(HttpServletRequest request) {
        return EXCLUDE_URLS.stream().anyMatch(url -> request.getRequestURI().equals(url));
    }

    private boolean isNotAllowRefresh(HttpServletRequest request) {
        return REFRESH_URLS.stream().noneMatch(url -> request.getRequestURI().equals(url));
    }

}