package com.kcs.search.utils;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IndexUtil {

    public static IndexCoordinates createIndexNameWithPostFixWrapper(String indexName) {
        Date now = new Date();

        // 현재 날짜/시간 출력
        System.out.println(now);

        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        // 포맷팅 적용
        String formatedNow = formatter.format(now);
        return IndexCoordinates.of(indexName + "-" + formatedNow);
    }

    public static IndexCoordinates createIndexNameWrapper(String indexName) {
        return IndexCoordinates.of(indexName);
    }

}
