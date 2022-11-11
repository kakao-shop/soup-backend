package com.example.soup.common.config;

import com.example.soup.api.member.support.LoginInterceptor;
import com.example.soup.api.member.support.MemberIdxDecodeResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> allowOriginUrlPatterns;
    private final LoginInterceptor loginInterceptor;

    private final MemberIdxDecodeResolver memberIdxDecodeResolver;


    public WebConfig(@Value("${cors.allow-origin.urls}") String allowOriginUrlPatterns,
                     LoginInterceptor loginInterceptor, MemberIdxDecodeResolver memberIdxDecodeResolver) {
        this.allowOriginUrlPatterns = Stream.of(allowOriginUrlPatterns.split(","))
                .map(String::strip)
                .collect(Collectors.toList());
        this.loginInterceptor = loginInterceptor;
        this.memberIdxDecodeResolver = memberIdxDecodeResolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**")
                .allowedMethods("*")
                .exposedHeaders(HttpHeaders.SET_COOKIE)
                .allowedOriginPatterns(allowOriginUrlPatterns.toArray(new String[0]));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/*","/**/**/*");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIdxDecodeResolver);
    }
}
