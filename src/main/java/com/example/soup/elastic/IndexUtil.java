package com.example.soup.elastic;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

public class IndexUtil {

    public static IndexCoordinates createIndexNameWithPostFixWrapper(String indexName) {
        Date now = new Date();

        // 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:43:32 KST 2022

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
