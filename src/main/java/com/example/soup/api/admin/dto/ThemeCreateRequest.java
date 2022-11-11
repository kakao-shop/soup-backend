package com.example.soup.api.admin.dto;

import com.example.soup.api.entity.mariadb.Theme;
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
