package com.kcs.common.entity.redis;

import com.kcs.common.entity.constant.Role;
import com.kcs.common.entity.mysql.Member;
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

    public static MemberTokenInfo of(String accessToken, String refreshToken, Member member) {
        return MemberTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberIdx(member.getMemberIdx())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }


    public boolean isSameToken(String refreshToken, String accessToken) {
        return this.refreshToken.equals(refreshToken)
                && this.accessToken.equals(accessToken);
    }
}