package com.kcs.member.service;

import com.kcs.member.dto.TokenDto;
import com.kcs.member.dto.request.LoginRequest;
import com.kcs.member.dto.response.LoginResponse;
import com.kcs.member.repository.MemberAuthRepository;
import com.kcs.member.repository.MemberRepository;
import com.kcs.common.entity.mysql.Member;
import com.kcs.common.entity.redis.MemberTokenInfo;
import com.kcs.common.exception.ExpiredRefreshTokenException;
import com.kcs.common.exception.InvalidTokenException;
import com.kcs.common.exception.NoRefreshTokenException;
import com.kcs.common.exception.NoSuchMemberExistException;
import com.kcs.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.UUID;

@Service
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
        MemberTokenInfo memberTokenInfo = MemberTokenInfo.of(accessToken, refreshToken, findMember);
        saveToken(memberTokenInfo);
        findMember.updateAccessInfo();
        return LoginResponse.from(memberTokenInfo);
    }

    private Member validateMemberExist(String id) {
        return memberRepository.findById(id).orElseThrow(NoSuchMemberExistException::new);
    }


    @Transactional
    public TokenDto createToken(Cookie cookie, String accessToken) {
        String refreshToken = getRefreshToken(cookie);
        MemberTokenInfo memberTokenInfo = memberAuthRepository.findById(refreshToken)
                .orElseThrow(ExpiredRefreshTokenException::new);
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
