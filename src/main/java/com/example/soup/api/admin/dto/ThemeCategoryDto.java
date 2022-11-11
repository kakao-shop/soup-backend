package com.example.soup.api.admin.dto;

import com.example.soup.api.entity.mariadb.Theme;
import com.example.soup.api.entity.mariadb.ThemeCategory;
import lombok.AllArgsConstructor;

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
