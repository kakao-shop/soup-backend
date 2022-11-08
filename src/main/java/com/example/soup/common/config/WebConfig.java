package com.example.soup.common.config;

import com.example.soup.member.support.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
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


    public WebConfig(@Value("${cors.allow-origin.urls}") String allowOriginUrlPatterns, LoginInterceptor loginInterceptor) {
        this.allowOriginUrlPatterns = Stream.of(allowOriginUrlPatterns.split(","))
                .map(String::strip)
                .collect(Collectors.toList());
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .exposedHeaders(HttpHeaders.LOCATION)
                .allowedOriginPatterns(allowOriginUrlPatterns.toArray(new String[0]));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/*");

    }
}
