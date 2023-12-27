package com.vt.valuetogether.global.config;

import com.vt.valuetogether.domain.oauth.handler.OAuth2LoginFailureHandler;
import com.vt.valuetogether.domain.oauth.handler.OAuth2LoginSuccessHandler;
import com.vt.valuetogether.domain.oauth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final OAuth2Service oAuth2Service;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable());

        httpSecurity.sessionManagement(
                (sessionManageMent) ->
                        sessionManageMent.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(
                (authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll() // resources 접근 허용 설정
                                .requestMatchers("/api/v1/users/**")
                                .permitAll() // '/api/v1/users/'로 시작하는 요청 모두 접근 허가
                                .anyRequest()
                                .authenticated() // 그 외 모든 요청 인증처리
                );
        httpSecurity.oauth2Login(
                (oauth2) ->
                        oauth2
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(
                                        userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2Service)));

        return httpSecurity.build();
    }
}
