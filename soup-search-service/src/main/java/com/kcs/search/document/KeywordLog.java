package com.kcs.search.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Document(indexName = "keywordlogs", createIndex = true)
@Setter
@Getter
@Builder
@AllArgsConstructor
public class KeywordLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    private Long memberidx;
    private String keyword;
    private int count;
    @Field(type = FieldType.Date, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updateat;

}
