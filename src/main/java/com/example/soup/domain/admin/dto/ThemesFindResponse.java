package com.example.soup.domain.admin.dto;

import com.example.soup.domain.entity.mariadb.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemesFindResponse {
    private List<Theme> themeList;
}
