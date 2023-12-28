package com.vt.valuetogether.global.jwt;

import static com.vt.valuetogether.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static com.vt.valuetogether.global.jwt.JwtUtil.AUTHORIZATION_KEY;
import static com.vt.valuetogether.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.vt.valuetogether.global.redis.RedisUtil;
import com.vt.valuetogether.global.validator.TokenValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/** 헤더에서 JWT 가져와서 인증 객체 생성 */
@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final List<RequestMatcher> whiteList =
            List.of(new AntPathRequestMatcher("/api/v1/users/signup", HttpMethod.POST.name()));
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        log.info("accessToken : {}", accessToken);
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        log.info("refreshToken : {}", refreshToken);

        // 요청 헤더에 access token 없거나 레디스에 access token 없으면 인증 미처리
        if (isAuthPass(accessToken, refreshToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = jwtUtil.substringToken(accessToken);
        refreshToken = jwtUtil.substringToken(refreshToken);

        // 로그아웃 처리된 경우
        TokenValidator.checkValidToken(redisUtil.hasKey(refreshToken));
        // access token 유효하지 않은 경우
        TokenValidator.checkValidToken(jwtUtil.isTokenValid(accessToken));

        // access token 만료되어 재발급 받는 경우
        if (jwtUtil.isTokenExpired(accessToken)) {
            TokenValidator.checkValidToken(isRefreshTokenValid(refreshToken));
            TokenValidator.checkExpiredToken(jwtUtil.isTokenExpired(refreshToken));

            accessToken = jwtUtil.substringToken(renewAccessToken(accessToken));
            response.addHeader(ACCESS_TOKEN_HEADER, accessToken);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(accessToken);
        String username = claims.getSubject();
        setAuthentication(username);

        filterChain.doFilter(request, response);
    }

    private boolean isAuthPass(String accessToken, String refreshToken) {
        return !StringUtils.hasText(accessToken)
                || !StringUtils.hasText(refreshToken)
                || !redisUtil.hasKey(jwtUtil.substringToken(refreshToken));
    }

    private String renewAccessToken(String accessToken) {
        Claims claims = jwtUtil.getUserInfoFromToken(accessToken);
        String username = claims.getSubject();
        String role = (String) claims.get(AUTHORIZATION_KEY);

        return jwtUtil.createAccessToken(username, role);
    }

    private boolean isRefreshTokenValid(String refreshToken) {
        return StringUtils.hasText(refreshToken)
                && jwtUtil.isTokenValid(refreshToken)
                && redisUtil.hasKey(refreshToken);
    }

    /**
     * 인증 처리
     *
     * @param username JWT 속의 Subject 에 저장된 username
     */
    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    /** 인증 객체 생성 */
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /** shouldNotFilter 는 true 를 반환하면 필터링 통과시키는 메서드. */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 현재 URL 이 화이트 리스트에 존재하는지 체크
        return whiteList.stream().anyMatch(whitePath -> whitePath.matches(request));
    }
}
