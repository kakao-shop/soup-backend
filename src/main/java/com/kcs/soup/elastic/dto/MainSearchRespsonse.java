package com.kcs.soup.elastic.dto;

import com.kcs.soup.api.entity.mariadb.Theme;
import com.kcs.soup.elastic.document.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MainSearchRespsonse {
    private List<Theme> themeList;
    private boolean isUserBest;
    private List<Product> recommendResult;
}
