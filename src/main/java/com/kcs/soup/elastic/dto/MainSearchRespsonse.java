package com.kcs.soup.elastic.dto;

import com.kcs.soup.api.entity.mariadb.Theme;
import com.kcs.soup.elastic.document.Product;
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
        System.out.println(min);
        localTime = localTime.withMinute(min);
        System.out.println(localTime);
        this.crawlingTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.themeList = themeList;
        this.isUserBest = isUserBest;
        this.recommendResult = recommendResult;
    }
}
