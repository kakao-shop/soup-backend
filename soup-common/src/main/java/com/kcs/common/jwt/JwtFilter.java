package com.kcs.common.jwt;

import com.kcs.common.dto.BaseResponse;
import com.kcs.common.dto.ErrorCode;
import com.kcs.common.exception.ExpiredAccessTokenException;
import com.kcs.common.exception.InvalidTokenException;
import com.kcs.common.exception.NoSuchMemberExistException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.getJwt();
            if (token != null && !token.isBlank() && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication();
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (ExpiredAccessTokenException | ExpiredJwtException e) {
            BaseResponse.error((HttpServletResponse) response, ErrorCode.ExpiredAccessJwt, HttpServletResponse.SC_BAD_REQUEST);
        } catch (JwtException | InvalidTokenException | NoSuchMemberExistException e) {
            BaseResponse.error((HttpServletResponse) response, ErrorCode.JwtValidation, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
