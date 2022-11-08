package com.example.soup.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class CookieTokenProvider {

    private final Long expireLength;

    public CookieTokenProvider(@Value("${jwt.refresh-token.expire-length}") String expireLength) {
        this.expireLength = Long.parseLong(expireLength);
    }

    public void set(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(expireLength)
                .path("/")
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.name())
                .httpOnly(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
