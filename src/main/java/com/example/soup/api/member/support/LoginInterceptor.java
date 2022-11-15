package com.example.soup.api.member.support;

import com.example.soup.common.exceptions.ExpiredAccessTokenException;
import com.example.soup.api.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger kafkaLogger = LoggerFactory.getLogger("kafkaLogger");
    private final JwtTokenProvider jwtTokenProvider;

    @Override


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        kafkaLogger.info("url : {}, method : {}", request.getRequestURI(), request.getMethod());
        String token = jwtTokenProvider.getJwt();
        if (token == null) {
            return true;
        }
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new ExpiredAccessTokenException();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
