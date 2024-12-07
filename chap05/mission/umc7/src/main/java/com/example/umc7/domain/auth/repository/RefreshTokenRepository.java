package com.example.umc7.domain.auth.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    @Value("${jwt.refresh-token.expire-length}")
    private Long REFRESH_EXPIRATION;
    private static final String REFRESH_TOKEN_KEY_NAME_PREFIX = "REFRESH:";

    private final RedisTemplate redisTemplate;

    public void save(String clientId, String refreshToken) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY_NAME_PREFIX + clientId,
            refreshToken, REFRESH_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    public String findById(String clientId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String findRefreshToken = valueOperations.get(REFRESH_TOKEN_KEY_NAME_PREFIX + clientId);
        return findRefreshToken;
    }

    public void deleteById(String clientId) {
        redisTemplate.delete(REFRESH_TOKEN_KEY_NAME_PREFIX + clientId);
    }
}