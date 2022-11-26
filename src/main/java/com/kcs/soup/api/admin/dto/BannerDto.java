package com.kcs.soup.api.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class BannerDto {
    private String title;
    private String path;
    private String contentType;
}
