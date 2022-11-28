package com.kcs.search.dto.response;

import common.entity.mysql.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MainThemeResponse {
    private Long idx;

    private String title;

    private String banner;

    public static MainThemeResponse of(Theme theme) {
        return MainThemeResponse.builder()
                .idx(theme.getIdx())
                .title(theme.getTitle())
                .banner(theme.getBanner().getPath())
                .build();
    }
}
