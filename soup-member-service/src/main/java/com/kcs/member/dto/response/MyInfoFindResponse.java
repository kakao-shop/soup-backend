package com.kcs.member.dto.response;


import com.kcs.common.entity.constant.Gender;
import com.kcs.common.entity.mysql.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MyInfoFindResponse {
    private String id;
    private String nickname;
    private String birthday;
    private Gender gender;

    public static MyInfoFindResponse from(Member member) {
        return MyInfoFindResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .birthday(String.valueOf(member.getBirthday()))
                .gender(member.getGender())
                .build();
    }
}
