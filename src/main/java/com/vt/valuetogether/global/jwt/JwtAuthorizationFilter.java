package com.vt.valuetogether.global.jwt;

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
@Slf4j(topic = "JWT 인증")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final List<RequestMatcher> whiteList =
            List.of(new AntPathRequestMatcher("/api/v1/users/signup", HttpMethod.POST.name()));
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader(JwtUtil.ACCESS_TOKEN_HEADER); // 헤더에서 JWT 가져오기
        log.info("accessToken : {}", accessToken);

        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = jwtUtil.substringToken(accessToken); // Bearer 접두사 제거

        if (!jwtUtil.isTokenValid(accessToken)) {
            throw new IllegalArgumentException("잘못된 토큰");
        }

        Claims info = jwtUtil.getUserInfoFromToken(accessToken);
        setAuthentication(info.getSubject());

        filterChain.doFilter(request, response);
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
