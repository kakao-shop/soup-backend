package com.kcs.admin.dto.request;

import com.kcs.admin.dto.ThemeCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ThemeCreateRequest {

    private String title;

    private List<ThemeCategoryDto> categoryList;

}
