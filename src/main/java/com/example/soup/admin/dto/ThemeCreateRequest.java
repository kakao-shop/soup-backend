package com.example.soup.admin.dto;

import com.example.soup.domain.entity.mariadb.Theme;
import com.example.soup.domain.entity.mariadb.ThemeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ThemeCreateRequest {

    private String title;

    private List<ThemeCategoryDto> categoryList;

    public Theme toThemeEntity() {
        return Theme.builder()
                .title(title)
                .build();
    }

}
