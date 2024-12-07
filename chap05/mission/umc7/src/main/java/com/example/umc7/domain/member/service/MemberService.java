package com.example.umc7.domain.member.service;

import com.example.umc7.domain.auth.dto.TokenResponseDto;
import com.example.umc7.domain.auth.repository.RefreshTokenRepository;
import com.example.umc7.domain.auth.jwt.JwtUtil;
import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.dto.LoginSuccessDto;
import com.example.umc7.domain.member.enums.Role;
import com.example.umc7.domain.member.repository.MemberRepository;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String JSON_ATTRIBUTE_NAME_NICKNAME = "nickname";
    private static final String JSON_ATTRIBUTE_NAME_ID = "id";
    private static final String JSON_ATTRIBUTE_NAME_PROFILE_IMAGE = "profile_image";

    @Transactional
    public Member findMember(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

    public LoginSuccessDto login(Map<String, String> memberInfo) {
        String clientId = memberInfo.get(JSON_ATTRIBUTE_NAME_ID);
        String kakaoProfileNickname = memberInfo.get(JSON_ATTRIBUTE_NAME_NICKNAME);

        String accessToken = jwtUtil.generateAccessToken(clientId);
        String refreshToken = jwtUtil.generateRefreshToken(clientId);

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        //redis에 refresh token 저장
        //key: clientId, value: refresh token
        refreshTokenRepository.save(clientId, refreshToken);

        Optional<Member> findMember = memberRepository.findByClientId(clientId);

        if (findMember.isPresent()) {
            // 최초 로그인이 아닌 사용자의 경우 카카오에서 사용하는 닉네임을 변경했을 가능성이 있기 때문에 로그인 시 업데이트 해준다.
            Member member = findMember.get();
            member.updateKakaoProfileNickname(kakaoProfileNickname);

            // 추가 정보 입력한 사용자일 경우
            if (member.getName() != null) {
                // jwt 엑세스 토큰 응답
                return LoginSuccessDto.builder()
                    .isFirstLogin(false)
                    .tokenResponseDto(tokenResponseDto)
                    .build();
            }

            // 소셜 로그인을 시도한 적이 있지만, 추가 정보 화면에서 나갔던 경우
            return LoginSuccessDto.builder()
                .isFirstLogin(true)
                .tokenResponseDto(tokenResponseDto)
                .build();
        }

        // 최초 로그인 하는 사용자의 경우 db에 사용자 정보를 저장한다.
        Member member = Member.builder()
            .clientId(clientId)
            .kakaoProfileNickname(kakaoProfileNickname)
            .role(Role.USER)
            .build();

        memberRepository.save(member);

        // jwt 엑세스 토큰 응답
        return LoginSuccessDto.builder()
            .isFirstLogin(true)
            .tokenResponseDto(tokenResponseDto)
            .build();
    }
}