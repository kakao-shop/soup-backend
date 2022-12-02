package com.kcs.search.utils;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IndexUtil {

    public static IndexCoordinates createIndexNameWithPostFixWrapper(String indexName) {
        Date now = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        String formatedNow = formatter.format(now);
        return IndexCoordinates.of(indexName + "-" + formatedNow);
    }

    public static IndexCoordinates createIndexNameWrapper(String indexName) {
        return IndexCoordinates.of(indexName);
    }

}
