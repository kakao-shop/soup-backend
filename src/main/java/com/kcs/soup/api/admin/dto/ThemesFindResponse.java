package com.kcs.soup.api.admin.dto;

import com.kcs.soup.common.entity.mysql.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemesFindResponse {
    private List<Theme> themeList;
}
