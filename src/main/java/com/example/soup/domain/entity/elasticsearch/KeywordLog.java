package com.example.soup.domain.entity.elasticsearch;

import com.example.soup.domain.entity.BaseCreatedTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Document(indexName = "keywordlogs", createIndex = true)
@Setter
@Getter
@Builder
@AllArgsConstructor
public class KeywordLog extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    private Long memberidx;
    private String keyword;
    private int count;
}
