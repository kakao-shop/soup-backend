package com.kcs.common.util;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class PreInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger("Interceptor logger");
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String> data = new HashMap<>();
        data.put("URI",request.getRequestURI());
        data.put("RemoteIP",request.getRemoteHost());
        logger.info("{}",data);

        return true;
    }
}
