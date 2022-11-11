package com.example.soup.api.member.dto.response;

import com.example.soup.api.entity.constant.Gender;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class MyInfoFindResponse {
    private String id;
    private String nickname;
    private String birthday;
    private Gender gender;
}
