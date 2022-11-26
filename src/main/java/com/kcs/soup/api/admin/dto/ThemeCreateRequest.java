package com.kcs.soup.api.admin.dto;

import com.kcs.soup.entity.mysql.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ThemeCreateRequest {

    private String title;

    private List<ThemeCategoryDto> categoryList;

}
