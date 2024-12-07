package com.example.umc7.global.config;

import com.example.umc7.domain.auth.jwt.JwtFilter;
import com.example.umc7.domain.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors((cors) -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(
                            Arrays.asList("http://localhost:5173"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }));

        //csrf disable
        httpSecurity
            .csrf((auth) -> auth.disable());

        //Form login 방식 disable
        httpSecurity
            .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        httpSecurity
            .httpBasic((auth) -> auth.disable());

        //session 설정 (jwt 사용 -> stateless)
        httpSecurity
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //경로별 인가 작업
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/"
                                , "/swagger-ui/**"
                                , "/v3/api-docs/**"
                                , "/v2/swagger-config"
                                , "/swagger-resources/**")
                        .permitAll()
                        .requestMatchers("/oauth2/kakao/**").permitAll()
                        .anyRequest().hasAnyAuthority("USER", "ADMIN")
                );

        // jwt필터 추가
        httpSecurity
            .addFilterBefore(new JwtFilter(jwtUtil, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        httpSecurity
            .exceptionHandling((exceptions) -> exceptions
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
            );

        return httpSecurity.build();
    }
}