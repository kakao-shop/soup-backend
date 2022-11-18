package com.kcs.soup.api.search.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BotThemeListResponse {
    private String value;

    private String label;

    private String trigger;
}
