package com.example.soup.member.service;

import com.example.soup.common.exceptions.InvalidTokenException;
import com.example.soup.common.exceptions.NoRefreshTokenException;
import com.example.soup.domain.MemberTokenInfo;
import com.example.soup.member.repository.MemberAuthRepository;
import com.example.soup.member.dto.TokenDto;
import com.example.soup.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberAuthRepository memberAuthRepository;


    public void saveToken(MemberTokenInfo memberTokenInfo) {
        memberAuthRepository.save(memberTokenInfo);
    }

    public TokenDto createToken(Cookie cookie, String accessToken) {
        String refreshToken = getRefreshToken(cookie);
        MemberTokenInfo memberTokenInfo = memberAuthRepository.findById(refreshToken)
                .orElseThrow(InvalidTokenException::new);
        validateMemberAuthInfo(memberTokenInfo, refreshToken, accessToken);
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

    private void validateMemberAuthInfo(MemberTokenInfo memberTokenInfo, String refreshToken, String accessToken) {
        if (!memberTokenInfo.isSameToken(refreshToken, accessToken)) {
            throw new InvalidTokenException();
        }
    }

    private MemberTokenInfo createNewToken(MemberTokenInfo memberTokenInfo) {
        Map<String, Object> claims = Map.of("memberIdx", memberTokenInfo.getMemberIdx(), "nickname", memberTokenInfo.getNickname(), "role", memberTokenInfo.getRole());
        String newAccessToken = jwtTokenProvider.createToken(claims);
        String newRefreshToken = UUID.randomUUID().toString();
        return MemberTokenInfo.builder()
                .refreshToken(newRefreshToken)
                .accessToken(newAccessToken)
                .memberIdx(memberTokenInfo.getMemberIdx())
                .nickname(memberTokenInfo.getNickname())
                .role(memberTokenInfo.getRole())
                .build();
    }

    public void deleteToken(Cookie cookie) {
        String refreshToken = getRefreshToken(cookie);
        memberAuthRepository.deleteById(refreshToken);
    }
}
