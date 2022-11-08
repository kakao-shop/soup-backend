package com.example.soup.member.dto.response;

import com.example.soup.domain.constant.Gender;
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
