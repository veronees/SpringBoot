package com.example.umc7.domain.auth.jwt;

import com.example.umc7.domain.auth.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public final static String TOKEN_TYPE_CLAIM_NAME = "tokenType";

    @Value("${jwt.custom.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.refresh-token.expire-length}")
    private Long REFRESH_EXPIRATION;

    @Value("${jwt.access-token.expire-length}")
    private Long ACCESS_EXPIRATION;

    public String generateAccessToken(String clientId) {
        return buildToken(TokenType.ACCESS, clientId, ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(String clientId) {
        return buildToken(TokenType.REFRESH, clientId, REFRESH_EXPIRATION);
    }

    private String buildToken(TokenType tokenType, String clientId, Long expiration) {
        return Jwts
            .builder()
            .setSubject(clientId)
            .claim(TOKEN_TYPE_CLAIM_NAME, tokenType.toString())
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    //서명 키 반환, 토큰 생성하고 검증할 때 사용
    private SecretKey getSignInKey() {
        String key = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(String token) {
        Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token);
    }

    public String extractClientId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //토큰에서 특정 클레임을 추출
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //토큰에서 모든 클레임을 추출
    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }


    // 토큰 타입 추출
    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE_CLAIM_NAME, String.class));
    }
}