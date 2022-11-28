package com.kcs.admin.dto.response;

import common.entity.mysql.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemesFindResponse {
    private List<Theme> themeList;

}
