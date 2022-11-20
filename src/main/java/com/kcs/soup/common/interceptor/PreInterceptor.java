package com.kcs.soup.common.interceptor;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURI().toLowerCase()+ " | " + request.getMethod() + " | " + request.getAuthType()
                + " | " + request.getQueryString().getBytes(StandardCharsets.UTF_8) + " | "  + request.getRemoteAddr());
        log.info("uri : " + request.getRequestURI().toLowerCase());
        return true;
    }
}
