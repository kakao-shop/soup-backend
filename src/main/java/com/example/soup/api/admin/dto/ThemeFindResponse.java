package com.example.soup.api.admin.dto;

import com.example.soup.api.entity.mariadb.ThemeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemeFindResponse {
    private String themeTitle;
    private List<ThemeCategory> themeList;
}
