package com.example.soup.api.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemeFindResponse {
    private String themeTitle;
    private List<ThemeCategoryDto> categoryList;
}
