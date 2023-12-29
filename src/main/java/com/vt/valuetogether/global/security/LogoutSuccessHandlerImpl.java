package com.vt.valuetogether.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vt.valuetogether.domain.user.dto.response.UserLogoutRes;
import com.vt.valuetogether.global.meta.ResultCode;
import com.vt.valuetogether.global.response.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        SecurityContextHolder.clearContext();

        response.setStatus(ResultCode.SUCCESS.getStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            response
                    .getWriter()
                    .write(new ObjectMapper().writeValueAsString(RestResponse.success(new UserLogoutRes())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
