package com.kcs.search.dto.response;

import com.kcs.common.entity.mysql.Theme;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BotThemeListResponse {
    private String value;

    private String label;

    private String trigger;

    public static BotThemeListResponse from(Theme theme) {
        return BotThemeListResponse.builder()
                .value(String.valueOf(theme.getIdx()))
                .label(theme.getTitle())
                .trigger("last")
                .build();
    }
}
