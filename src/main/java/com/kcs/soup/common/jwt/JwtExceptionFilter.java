package com.kcs.soup.common.jwt;

import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.dto.ErrorCode;
import com.kcs.soup.common.exceptions.ExpiredAccessTokenException;
import com.kcs.soup.common.exceptions.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.getJwt();
            if (token != null) {
                jwtTokenProvider.validateToken(token);
            }
            chain.doFilter(request, response);
        } catch (ExpiredAccessTokenException | ExpiredJwtException e) {
            BaseResponse.error(response, ErrorCode.ExpiredJwt, HttpServletResponse.SC_BAD_REQUEST);
        } catch (JwtException | InvalidTokenException e) {
            BaseResponse.error(response, ErrorCode.JwtValidation, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
