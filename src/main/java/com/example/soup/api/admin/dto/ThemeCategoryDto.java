package com.example.soup.api.admin.dto;

import com.example.soup.api.entity.mariadb.Theme;
import com.example.soup.api.entity.mariadb.ThemeCategory;
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
