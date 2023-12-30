package com.vt.valuetogether.global.security;

import com.vt.valuetogether.global.jwt.JwtUtil;
import com.vt.valuetogether.global.redis.RedisUtil;
import com.vt.valuetogether.global.validator.TokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "localLogin logout")
@Component
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String accessToken =
                jwtUtil.getTokenWithoutBearer(request.getHeader(JwtUtil.ACCESS_TOKEN_HEADER));
        log.info("access token : {}", accessToken);

        TokenValidator.checkValidToken(jwtUtil.isTokenValid(accessToken));

        if (redisUtil.hasKey(accessToken)) {
            log.info("delete redis access token : {}", accessToken);
            redisUtil.delete(accessToken);
        }
    }
}
