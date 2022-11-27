package com.kcs.soup.api.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kcs.soup.common.entity.constant.Role;
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

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
