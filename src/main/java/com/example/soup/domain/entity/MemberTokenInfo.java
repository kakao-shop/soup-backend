package com.example.soup.domain.entity;

import com.example.soup.domain.constant.Role;
import com.example.soup.member.dto.TokenDto;
import com.example.soup.member.dto.response.LoginResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;


@Getter
@Setter
@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken",timeToLive = 1209600)
public class MemberTokenInfo {
    @Id
    private String refreshToken;
    private String accessToken;
    private Long memberIdx;
    private String nickname;
    private Role role;


    public boolean isSameToken(String refreshToken, String accessToken) {
        return this.refreshToken.equals(refreshToken)
                && this.accessToken.equals(accessToken);
    }

    public LoginResponse toLoginResponseDto(){
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberIdx(memberIdx)
                .nickname(nickname)
                .role(role)
                .build();
    }

    public TokenDto toTokenDto(){
        return TokenDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
}