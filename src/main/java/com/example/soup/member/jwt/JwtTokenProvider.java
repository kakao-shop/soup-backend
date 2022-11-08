package com.example.soup.member.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token.expire-length}") long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8)));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    // 토큰 생성
    public String createToken(Map<String, Object> payload) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // token으로부터 memberIdx 추출
    public Long getMemberIdx() {
        String token = getJwt();
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
        Long memberIdx = claims.get("memberIdx", Long.class);
        return memberIdx;
    }

    public Claims getClaims() {
        String token = getJwt();
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    // 헤더로부터 Access Token 값 가져오기
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("X-ACCESS-TOKEN");
        return accessToken;
    }

    // 토큰이 만료되었는지 검증
    public boolean validateToken(String token) {
        Claims claims = getClaims();
        boolean isValid = !claims.getExpiration().before(new Date());
        System.out.println("expire: " + claims.getExpiration());
        System.out.println("Date: " + new Date());
        return isValid;
    }
}

