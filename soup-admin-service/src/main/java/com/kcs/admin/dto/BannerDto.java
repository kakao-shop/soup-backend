package com.kcs.admin.dto;

import com.kcs.common.entity.mysql.Banner;
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

    public static BannerDto from(Banner banner) {
        return BannerDto.builder()
                .title(banner.getTitle())
                .path(banner.getPath())
                .contentType(banner.getContentType())
                .build();
    }
}
