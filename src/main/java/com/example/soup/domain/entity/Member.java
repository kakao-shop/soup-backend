package com.example.soup.domain.entity;

import com.example.soup.domain.constant.Gender;
import com.example.soup.domain.constant.Oauth;
import com.example.soup.domain.constant.Role;
import com.example.soup.member.dto.request.MyInfoUpdateRequest;
import com.example.soup.member.dto.response.MyInfoFindResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
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

    @Builder
    public Member(String id, String nickname, String password,
                  LocalDate birthday, Gender gender, Role role, Oauth oauth) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.role = role;
        this.oauth = oauth;
    }

    public void updateMember(MyInfoUpdateRequest myInfoUpdateRequest) {
        this.password = myInfoUpdateRequest.getPassword();
        this.nickname = myInfoUpdateRequest.getNickname();
    }

    public MyInfoFindResponse toMyInfoFindResponseDto(){
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
