package com.kcs.soup.api.member.dto.response;

import com.kcs.soup.api.entity.constant.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

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

    public void setRefreshTokenNull() {
        this.refreshToken = null;
    }
}
