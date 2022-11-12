package com.example.soup.elastic.dto;

import com.example.soup.api.entity.mariadb.Theme;
import com.example.soup.elastic.document.Product;
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
