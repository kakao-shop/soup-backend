package com.kcs.admin.dto;


import com.kcs.common.entity.mysql.ThemeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ThemeCategoryDto {
    private String mainCategory;

    private String subCategory;

    public static ThemeCategoryDto from(ThemeCategory category) {
        return ThemeCategoryDto.builder()
                .mainCategory(category.getMainCategory())
                .subCategory(category.getSubCategory())
                .build();
    }

}
