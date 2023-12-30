package com.vt.valuetogether.global.jwt;

import static com.vt.valuetogether.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static com.vt.valuetogether.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;
import static com.vt.valuetogether.global.redis.RedisUtil.ACCESS_TOKEN_EXPIRED_TIME;

import com.vt.valuetogether.global.redis.RedisUtil;
import com.vt.valuetogether.global.security.UserDetailsImpl;
import com.vt.valuetogether.global.validator.TokenValidator;
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
            List.of(new AntPathRequestMatcher("/api/v1/users/signup/**", HttpMethod.POST.name()));
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken =
                jwtUtil.getTokenWithoutBearer(request.getHeader(ACCESS_TOKEN_HEADER)); // access token 은 필수
        log.info("accessToken : {}", accessToken);

        String refreshToken =
                jwtUtil.getTokenWithoutBearer(
                        request.getHeader(REFRESH_TOKEN_HEADER)); // refresh token 은 선택
        log.info("refreshToken : {}", refreshToken);

        // access token 비어있으면 인증 미처리
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 우선 access token 으로 검증
        TokenValidator.checkValidToken(jwtUtil.isTokenValid(accessToken));

        // 로그아웃 처리된 경우 검증
        TokenValidator.checkLoginRequired(!redisUtil.hasKey(accessToken));

        // 유효한 access token 이면 인증 처리
        if (!jwtUtil.isTokenExpired(accessToken)) {
            setAuthentication(jwtUtil.getUsernameFromToken(accessToken));
            filterChain.doFilter(request, response);
            return;
        }

        // access token 이 만료되면 refresh token 으로 검증
        TokenValidator.checkValidToken(isRefreshTokenValid(refreshToken));

        // refresh token 이 만료되면 redis 에서 삭제(밴 처리) 후 로그인 필요 예외 발생
        TokenValidator.checkLoginRequired(isLoginRequired(refreshToken, accessToken));

        // 응답 헤더에 재발급한 access token 반환
        response.addHeader(ACCESS_TOKEN_HEADER, renewAccessToken(accessToken));
        filterChain.doFilter(request, response);
    }

    private boolean isLoginRequired(String refreshToken, String accessToken) {
        return jwtUtil.isTokenExpired(refreshToken) && redisUtil.delete(accessToken);
    }

    private String renewAccessToken(String prevAccessToken) {
        log.info("access token 재발급");
        String username = (String) redisUtil.get(prevAccessToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        String role = userDetails.getUser().getRole().getAuthority();
        String newAccessToken = jwtUtil.createAccessToken(username, role);

        redisUtil.delete(prevAccessToken);
        redisUtil.set(jwtUtil.substringToken(newAccessToken), username, ACCESS_TOKEN_EXPIRED_TIME);

        setAuthentication(username);
        return newAccessToken;
    }

    private boolean isRefreshTokenValid(String refreshToken) {
        return StringUtils.hasText(refreshToken) && jwtUtil.isTokenValid(refreshToken);
    }

    /**
     * 인증 처리
     *
     * @param username JWT 속의 Subject 에 저장된 username
     */
    private void setAuthentication(String username) {
        log.info("set auth username : {}", username);
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
