package com.kcs.common.jwt;

import com.kcs.common.dto.BaseResponse;
import com.kcs.common.dto.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseResponse.error(response, ErrorCode.UnAuthenticatedUser, HttpServletResponse.SC_UNAUTHORIZED);
    }
}

