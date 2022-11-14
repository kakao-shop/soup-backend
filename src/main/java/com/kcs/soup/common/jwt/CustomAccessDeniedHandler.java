package com.kcs.soup.common.jwt;

import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.dto.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponse.error(response, ErrorCode.UnAuthorizedUser, HttpServletResponse.SC_FORBIDDEN);
    }
}
