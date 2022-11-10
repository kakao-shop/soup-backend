package com.example.soup.elasticsearch;

import com.example.soup.domain.entity.BaseCreatedTimeEntity;
import com.example.soup.domain.entity.mariadb.Member;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@AllArgsConstructor
@Document(indexName = "keyword_logs")
public class KeywordLog extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long memberIdx;

    private String keyword;

}
