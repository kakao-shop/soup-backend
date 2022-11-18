package com.kcs.soup.elastic.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BotThemeListResponse {
    private String value;

    private String label;

    private String trigger;
}
