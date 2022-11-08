package com.example.soup.member.service;

import com.example.soup.common.exceptions.InvalidTokenException;
import com.example.soup.common.exceptions.NoRefreshTokenException;
import com.example.soup.domain.entity.MemberTokenInfo;
import com.example.soup.member.repository.MemberAuthRepository;
import com.example.soup.member.dto.TokenDto;
import com.example.soup.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberAuthRepository memberAuthRepository;

    public void saveToken(MemberTokenInfo memberTokenInfo) {
        memberAuthRepository.save(memberTokenInfo);
    }

    @Transactional
    public TokenDto createToken(Cookie cookie, String accessToken) {
        String refreshToken = getRefreshToken(cookie);
        MemberTokenInfo memberTokenInfo = memberAuthRepository.findById(refreshToken)
                .orElseThrow(InvalidTokenException::new);
        validateMemberAuthInfo(memberTokenInfo.getRefreshToken(), refreshToken);
        memberAuthRepository.delete(memberTokenInfo);
        MemberTokenInfo newMemberTokenInfo = createNewToken(memberTokenInfo);
        saveToken(newMemberTokenInfo);
        return new TokenDto(newMemberTokenInfo.getAccessToken(), newMemberTokenInfo.getRefreshToken());
    }

    public String getRefreshToken(Cookie cookie) {
        if (cookie == null) {
            throw new NoRefreshTokenException();
        }
        return cookie.getValue();
    }

    private void validateMemberAuthInfo(String refreshTokenFromCookie, String refreshTokenFromDB) {
        if (!refreshTokenFromCookie.equals(refreshTokenFromDB)) {
            throw new InvalidTokenException();
        }
    }

    @Transactional
    public MemberTokenInfo createNewToken(MemberTokenInfo memberTokenInfo) {
        Map<String, Object> claims = Map.of("memberIdx", memberTokenInfo.getMemberIdx(), "nickname", memberTokenInfo.getNickname(), "role", memberTokenInfo.getRole());
        String newAccessToken = jwtTokenProvider.createToken(claims);
        String newRefreshToken = UUID.randomUUID().toString();
        memberTokenInfo.setAccessToken(newAccessToken);
        memberTokenInfo.setRefreshToken(newRefreshToken);
        return memberTokenInfo;
    }

    @Transactional
    public void deleteToken(Cookie cookie) {
        String refreshToken = getRefreshToken(cookie);
        memberAuthRepository.deleteById(refreshToken);
    }
}
