package com.kcs.soup.api.search.dto;

import com.kcs.soup.api.search.document.Product;
import com.kcs.soup.entity.mysql.Theme;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class MainSearchRespsonse {
    private String crawlingTime;
    private List<Theme> themeList;
    private boolean isUserBest;
    private List<Product> recommendResult;

    public MainSearchRespsonse(List<Theme> themeList, boolean isUserBest, List<Product> recommendResult) {
        LocalTime localTime = LocalTime.now();
        int min = localTime.getMinute();
        min -= (min % 30);
        localTime = localTime.withMinute(min);

        this.crawlingTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.themeList = themeList;
        this.isUserBest = isUserBest;
        this.recommendResult = recommendResult;
    }
}