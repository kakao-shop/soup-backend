package com.example.soup.member.service;

import com.example.soup.common.exceptions.InvalidTokenException;
import com.example.soup.common.exceptions.NoRefreshTokenException;
import com.example.soup.common.exceptions.NoSuchMemberExistException;
import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.domain.entity.redis.MemberTokenInfo;
import com.example.soup.member.dto.TokenDto;
import com.example.soup.member.dto.request.LoginRequest;
import com.example.soup.member.dto.response.LoginResponse;
import com.example.soup.member.jwt.JwtTokenProvider;
import com.example.soup.member.repository.MemberAuthRepository;
import com.example.soup.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    private final MemberAuthRepository memberAuthRepository;

    public void saveToken(MemberTokenInfo memberTokenInfo) {
        memberAuthRepository.save(memberTokenInfo);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Member findMember = validateMemberExist(request.getId());
        if (findMember.isSamePassword(request.getPassword())) {
            throw new NoSuchMemberExistException();
        }

        Map<String, Object> claims = Map.of("memberIdx", findMember.getMemberIdx(), "role", findMember.getRole());
        String accessToken = jwtTokenProvider.createToken(claims);
        String refreshToken = UUID.randomUUID().toString();
        MemberTokenInfo memberTokenInfo = findMember.toMemberTokenInfoEntity(accessToken, refreshToken);
        saveToken(memberTokenInfo);
        return memberTokenInfo.toLoginResponseDto();
    }

    private Member validateMemberExist(String id) {
        return memberRepository.findById(id)
                .orElseThrow(NoSuchMemberExistException::new);
    }


    @Transactional
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

    private String getRefreshToken(Cookie cookie) {
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
