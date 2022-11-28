package com.kcs.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import common.entity.constant.Role;
import common.entity.redis.MemberTokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;
    private Long memberIdx;
    private String nickname;
    private Role role;

    public static LoginResponse from(MemberTokenInfo memberTokenInfo) {
        return LoginResponse.builder()
                .accessToken(memberTokenInfo.getAccessToken())
                .refreshToken(memberTokenInfo.getRefreshToken())
                .memberIdx(memberTokenInfo.getMemberIdx())
                .nickname(memberTokenInfo.getNickname())
                .role(memberTokenInfo.getRole())
                .build();

    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
