package com.example.soup.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken",timeToLive = 1209600)
public class MemberTokenInfo {
    @Id
    private String refreshToken;
    private String accessToken;
    private Long memberIdx;
    private String nickname;
    private String role;


    public boolean isSameToken(String refreshToken, String accessToken) {
        return this.refreshToken.equals(refreshToken)
                && this.accessToken.equals(accessToken);
    }
}