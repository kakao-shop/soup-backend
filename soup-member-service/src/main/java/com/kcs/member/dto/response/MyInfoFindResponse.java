package com.kcs.member.dto.response;


import com.kcs.common.entity.constant.Gender;
import com.kcs.common.entity.mysql.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@Slf4j
public class MyInfoFindResponse {
    private String id;
    private String nickname;
    private String birthday;
    private Gender gender;

    public static MyInfoFindResponse from(Member member) {
        log.info("input from database: " + String.valueOf(member.getBirthday()));
        return MyInfoFindResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .build();
    }
}
