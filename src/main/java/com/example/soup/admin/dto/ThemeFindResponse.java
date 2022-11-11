package com.example.soup.admin.dto;

import com.example.soup.domain.entity.mariadb.ThemeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemeFindResponse {
    private String themeTitle;
    private List<ThemeCategory> themeList;
}
