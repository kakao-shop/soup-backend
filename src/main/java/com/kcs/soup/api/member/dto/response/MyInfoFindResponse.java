package com.kcs.soup.api.member.dto.response;

import com.kcs.soup.common.entity.constant.Gender;
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
