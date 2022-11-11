package com.example.soup.api.admin.dto;

import com.example.soup.api.entity.mariadb.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemesFindResponse {
    private List<Theme> themeList;
}
