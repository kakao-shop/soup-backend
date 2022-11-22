package com.kcs.soup.api.search.dto;

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
}
