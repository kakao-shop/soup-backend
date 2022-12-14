package com.kcs.common.jwt;


import com.kcs.common.entity.mysql.Member;
import com.kcs.common.exception.NoAccessTokenException;
import com.kcs.common.exception.NoSuchMemberExistException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long validityInMilliseconds;

    private final JwtRepository jwtRepository;
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token.expire-length}") long validityInMilliseconds,
                            JwtRepository jwtRepository) {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8)));
        this.validityInMilliseconds = validityInMilliseconds;
        this.jwtRepository = jwtRepository;
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
        if (token == null) {
            throw new NoAccessTokenException();
        }

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("memberIdx", Long.class);
    }

    public Long getMemberIdxIfLogined() {
        String token = getJwt();
        if ((token == null) || token.isBlank()) {
            return null;
        }
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("memberIdx", Long.class);
    }


    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public Authentication getAuthentication() {
        Long memberIdx = getMemberIdx();
        Member member = jwtRepository.findByMemberIdx(memberIdx)
                .orElseThrow(NoSuchMemberExistException::new);
        PrincipleDetails principal = new PrincipleDetails(member);
        Authentication auth = new UsernamePasswordAuthenticationToken(member.getId(), null, principal.getAuthorities());
        return auth;
    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        boolean isValid = !claims.getExpiration().before(new Date());
        return isValid;
    }

}

