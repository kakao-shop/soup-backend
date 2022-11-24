package com.kcs.soup.api.search.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Document(indexName = "selectitemlog", createIndex = true, replicas = 2)
@Setter
@Getter
@AllArgsConstructor
@Builder

public class SelectItemLog {
    @Id
    @GeneratedValue
    private String id;
    private Long memberidx;
    private String itemurl;
    private int count;

    private String prdName;
    private Long price;
    private String cat;
    private Long purchase;
    private float score;
    private String subcat;
    private String imgSrc;
    private String site;
    private String webUrl;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updateat;

    public void updateTime() {
        this.updateat = LocalDateTime.now();
    }



}
