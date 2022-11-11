package com.example.soup.elastic;

import com.example.soup.domain.entity.BaseCreatedTimeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Document(indexName = "keywordlogs",createIndex = true)
@Setter
@Getter
public class KeywordLog extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    private Long memberidx;
    private String keyword;
    private int count;
}