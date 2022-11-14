package com.kcs.soup.api.admin.dto;

import com.kcs.soup.api.entity.mariadb.Theme;
import com.kcs.soup.api.entity.mariadb.ThemeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ThemeCategoryDto {
    private String mainCategory;
    private String subCategory;

    public ThemeCategory toThemeCategoryEntity(Theme theme) {
        return ThemeCategory.builder()
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .theme(theme)
                .build();
    }
}
