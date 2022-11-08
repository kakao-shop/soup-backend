package com.example.soup.member.support;

import com.example.soup.common.exceptions.ExpiredAccessTokenException;
import com.example.soup.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = jwtTokenProvider.getJwt();
        if (token == null) {
            return true;
        }
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new ExpiredAccessTokenException();
    }
}
