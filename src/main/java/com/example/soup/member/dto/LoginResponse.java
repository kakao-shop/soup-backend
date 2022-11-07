package com.example.soup.member.dto;

import com.example.soup.domain.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
