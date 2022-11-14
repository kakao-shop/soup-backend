package com.kcs.soup.common.jwt;

import com.kcs.soup.api.entity.mariadb.Member;
import com.kcs.soup.api.member.repository.MemberRepository;
import com.kcs.soup.common.exceptions.NoAccessTokenException;
import com.kcs.soup.common.exceptions.NoSuchMemberExistException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
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

    private final MemberRepository memberRepository;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token.expire-length}") long validityInMilliseconds,
                            MemberRepository memberRepository) {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8)));
        this.validityInMilliseconds = validityInMilliseconds;
        this.memberRepository = memberRepository;
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
        Member member = memberRepository.findByMemberIdx(memberIdx)
                .orElseThrow(NoSuchMemberExistException::new);
        PrincipleDetails principal = new PrincipleDetails(member);
        Authentication auth = new UsernamePasswordAuthenticationToken(member.getId(), null, principal.getAuthorities());
        return auth;
    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        boolean isValid = !claims.getExpiration().before(new Date());
        System.out.println("expire: " + claims.getExpiration());
        System.out.println("Date: " + new Date());
        return isValid;
    }

}

