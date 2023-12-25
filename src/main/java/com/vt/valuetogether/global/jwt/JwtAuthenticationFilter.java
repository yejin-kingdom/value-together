package com.vt.valuetogether.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vt.valuetogether.domain.user.dto.request.UserLocalLoginReq;
import com.vt.valuetogether.domain.user.dto.response.UserLocalLoginRes;
import com.vt.valuetogether.domain.user.entity.Role;
import com.vt.valuetogether.global.meta.ResultCode;
import com.vt.valuetogether.global.response.RestResponse;
import com.vt.valuetogether.global.security.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void setup() {
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLocalLoginReq req =
                    new ObjectMapper().readValue(request.getInputStream(), UserLocalLoginReq.class);

            return getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword(), null));

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException {

        UserLocalLoginRes res = createTokens(authResult);
        settingResponse(response, RestResponse.success(res));
    }

    private UserLocalLoginRes createTokens(Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role);

        return UserLocalLoginRes.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {

        response.setStatus(401);
        settingResponse(response, RestResponse.error(ResultCode.NOT_FOUND_USER));
    }

    private void settingResponse(HttpServletResponse response, RestResponse<?> res)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String result = objectMapper.writeValueAsString(res);
        response.getWriter().write(result);
    }
}
