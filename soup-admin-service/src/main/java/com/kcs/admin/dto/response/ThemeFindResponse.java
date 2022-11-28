package com.kcs.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kcs.admin.dto.BannerDto;
import com.kcs.admin.dto.ThemeCategoryDto;
import common.entity.mysql.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
public class ThemeFindResponse {
    private String title;

    private List<ThemeCategoryDto> categoryList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BannerDto banner;

    public static ThemeFindResponse from(Theme theme) {
        return ThemeFindResponse.builder()
                .title(theme.getTitle())
                .categoryList(
                        theme.getCategories()
                                .stream()
                                .map(ThemeCategoryDto::from)
                                .collect( Collectors.toList()))
                .banner(BannerDto.from(theme.getBanner()))
                .build();
    }
}
