package com.example.umc7.domain.auth.service;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.auth.dto.TokenResponseDto;
import com.example.umc7.domain.auth.repository.RefreshTokenRepository;
import com.example.umc7.domain.auth.jwt.JwtUtil;
import com.example.umc7.domain.member.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class AuthService {

    private static final String QUERY_PARAMETER_NAME_CLIENT_ID = "client_id";
    private static final String QUERY_PARAMETER_NAME_REDIRECT_URI = "redirect_uri";
    private static final String QUERY_PARAMETER_NAME_RESPONSE_TYPE = "response_type";
    private static final String QUERY_PARAMETER_VALUE_CODE = "code";

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/x-www-form-urlencoded;charset=utf-8";

    private static final String BODY_ATTRIBUTE_NAME_GRANT_TYPE = "grant_type";
    private static final String BODY_ATTRIBUTE_VALUE_AUTH = "authorization_code";
    private static final String BODY_ATTRIBUTE_NAME_CLIENT_SECRET = "client_secret";
    private static final String BODY_ATTRIBUTE_NAME_CODE = "code";

    private static final String HEADER_ATTRIBUTE_NAME_AUTH = "Authorization";
    private static final String HEADER_TOKEN_PREFIX = "Bearer ";

    private static final String JSON_ATTRIBUTE_NAME_TOKEN = "access_token";
    private static final String JSON_ATTRIBUTE_NAME_ID = "id";
    private static final String JSON_ATTRIBUTE_NAME_PROPERTIES = "properties";
    private static final String JSON_ATTRIBUTE_NAME_NICKNAME = "nickname";
    private static final String JSON_ATTRIBUTE_NAME_PROFILE_IMAGE = "profile_image";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;
    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String AUTHORIZATION_URI;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String TOKEN_URI;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String USER_INFO_URI;

    private final RestTemplate restTemplate;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public AuthService(RestTemplateBuilder restTemplateBuilder,
        RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.restTemplate = restTemplateBuilder.build();
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public String getRedirectUrl() {
        String url = UriComponentsBuilder.fromHttpUrl(AUTHORIZATION_URI)
            .queryParam(QUERY_PARAMETER_NAME_CLIENT_ID, KAKAO_CLIENT_ID)
            .queryParam(QUERY_PARAMETER_NAME_REDIRECT_URI, KAKAO_REDIRECT_URI)
            .queryParam(QUERY_PARAMETER_NAME_RESPONSE_TYPE, QUERY_PARAMETER_VALUE_CODE)
            .build()
            .toString();

        return url;
    }

    public Map<String, String> getMemberInfo(String code) throws JsonProcessingException {
        // 카카오 측에 access 토큰 요청을 위한 요청 httpEntity 생성
        HttpEntity<MultiValueMap<String, String>> tokenRequest = generateTokenRequest(code);

        // accessToken 요청 작업 시작
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(TOKEN_URI,
            HttpMethod.POST,
            tokenRequest, String.class);

        // 위 요청에서 받은 응닶 값에서 accessToken 파싱
        String accessToken = parseAccessToken(accessTokenResponse);

        // accessToken으로 사용자 정보 조회를 위한 요청 httpEntity 생성
        HttpEntity<MultiValueMap<String, String>> memberInfoRequest = generateMemberInfoRequest(
            accessToken);

        // 사용자 정보 요청과 응답
        ResponseEntity<String> memberInfoResponse = restTemplate.exchange(USER_INFO_URI,
            HttpMethod.POST,
            memberInfoRequest, String.class);

        log.info("로그인 사용자 정보 : {}", memberInfoResponse);

        return getClientId(memberInfoResponse);
    }

    public TokenResponseDto reissue(String refreshToken) {
        String clientId = jwtUtil.extractClientId(refreshToken);

        // redis에서 해당 유저의 refresh token이 있는지 조회
        String findRefreshToken = refreshTokenRepository.findById(clientId);

        // redis에서 조회된 결과 없으면 예외
        if (Objects.isNull(findRefreshToken)) {
            throw new GeneralException(ErrorStatus.MEMBER_TOKEN_NOT_FOUND);
        }

        String accessToken = jwtUtil.generateAccessToken(clientId);

        // refresh token 존재하면 accessToken 재발급 + refreshToken은 원래 토큰 전달
        return TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public void deleteRefreshToken(Member member) {
        String clientId = member.getClientId();
        String findRefreshToken = refreshTokenRepository.findById(clientId);

        if (Objects.isNull(findRefreshToken)) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_LOGOUT);
        }

        refreshTokenRepository.deleteById(clientId);
    }

    private HttpEntity<MultiValueMap<String, String>> generateTokenRequest(String code) {
        // HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE);

        // HTTP Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(BODY_ATTRIBUTE_NAME_GRANT_TYPE, BODY_ATTRIBUTE_VALUE_AUTH);
        body.add(QUERY_PARAMETER_NAME_CLIENT_ID, KAKAO_CLIENT_ID);
        body.add(QUERY_PARAMETER_NAME_REDIRECT_URI, KAKAO_REDIRECT_URI);
        body.add(BODY_ATTRIBUTE_NAME_CODE, code);
        body.add(BODY_ATTRIBUTE_NAME_CLIENT_SECRET, KAKAO_CLIENT_SECRET);

        return new HttpEntity<>(body, headers);
    }

    private String parseAccessToken(ResponseEntity<String> response)
        throws JsonProcessingException {
        String responseBody = parseResponseBody(response);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get(JSON_ATTRIBUTE_NAME_TOKEN).asText();
    }

    private HttpEntity<MultiValueMap<String, String>> generateMemberInfoRequest(
        String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_ATTRIBUTE_NAME_AUTH, HEADER_TOKEN_PREFIX + accessToken);
        headers.add(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE);

        return new HttpEntity<>(headers);
    }

    private Map<String, String> getClientId(ResponseEntity<String> response)
        throws JsonProcessingException {
        String responseBody = parseResponseBody(response);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String id = jsonNode.get(JSON_ATTRIBUTE_NAME_ID).asText();
        String clientId = generateClientId(id);

        String nickname = jsonNode.get(JSON_ATTRIBUTE_NAME_PROPERTIES)
            .get(JSON_ATTRIBUTE_NAME_NICKNAME).asText();

        String profileImage = jsonNode.get(JSON_ATTRIBUTE_NAME_PROPERTIES)
            .get(JSON_ATTRIBUTE_NAME_PROFILE_IMAGE).asText();

        HashMap<String, String> memberInfoMap = new HashMap<>();
        memberInfoMap.put(JSON_ATTRIBUTE_NAME_ID, clientId);
        memberInfoMap.put(JSON_ATTRIBUTE_NAME_NICKNAME, nickname);
        memberInfoMap.put(JSON_ATTRIBUTE_NAME_PROFILE_IMAGE, profileImage);

        return memberInfoMap;
    }

    private String parseResponseBody(ResponseEntity<String> response) {
        String responseBody = response.getBody();
        return responseBody;
    }

    private String generateClientId(String id) {
        return id + ":KAKAO";
    }
}