package com.kcs.soup.entity.mysql;

import com.kcs.soup.api.member.dto.request.MyInfoUpdateRequest;
import com.kcs.soup.api.member.dto.response.MyInfoFindResponse;
import com.kcs.soup.entity.BaseTImeEntity;
import com.kcs.soup.entity.constant.Gender;
import com.kcs.soup.entity.constant.Oauth;
import com.kcs.soup.entity.constant.Role;
import com.kcs.soup.entity.redis.MemberTokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTImeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(length = 10)
    private String nickname;

    @Column(length = 20)
    private String id;

    private String password;

    private LocalDate birthday;

    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private Oauth oauth;

    private Long totalAccessCnt;

    private LocalDateTime lastAccessTime;

    public void updateMember(MyInfoUpdateRequest myInfoUpdateRequest) {
        this.password = myInfoUpdateRequest.getPassword();
    }

    public void updateAccessInfo() {
        this.totalAccessCnt += 1;
        this.lastAccessTime = LocalDateTime.now();
    }

    public boolean isSamePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return !encoder.matches(password, this.password);
    }

    public MyInfoFindResponse toMyInfoFindResponseDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return MyInfoFindResponse.builder()
                .id(id)
                .nickname(nickname)
                .birthday(formatter.format(birthday))
                .gender(gender)
                .build();
    }

    public MemberTokenInfo toMemberTokenInfoEntity(String accessToken, String refreshToken) {
        return MemberTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberIdx(memberIdx)
                .nickname(nickname)
                .role(role)
                .build();
    }
}
