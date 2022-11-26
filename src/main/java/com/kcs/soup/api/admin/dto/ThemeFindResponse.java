package com.kcs.soup.api.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kcs.soup.entity.mysql.Banner;
import com.kcs.soup.entity.mysql.ThemeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ThemeFindResponse {
    private String title;

    private List<ThemeCategoryDto> categoryList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BannerDto banner;
}
