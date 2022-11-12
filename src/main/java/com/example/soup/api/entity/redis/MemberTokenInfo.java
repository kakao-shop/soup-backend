package com.example.soup.api.entity.redis;

import com.example.soup.api.entity.constant.Role;
import com.example.soup.api.member.dto.TokenDto;
import com.example.soup.api.member.dto.response.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class MemberTokenInfo {
    @Id
    private String refreshToken;
    private String accessToken;
    private Long memberIdx;
    private String nickname;
    private Role role;

    public LoginResponse toLoginResponseDto() {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberIdx(memberIdx)
                .nickname(nickname)
                .role(role)
                .build();
    }

    public TokenDto toTokenDto() {
        return TokenDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public boolean isSameToken(String refreshToken, String accessToken) {
        return this.refreshToken.equals(refreshToken)
                && this.accessToken.equals(accessToken);
    }
}