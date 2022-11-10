package com.example.soup.elastic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Document(indexName = "product-", createIndex = false)
@Setter
@Getter
@Builder
public class ProductDocs {
    @Id @GeneratedValue(strategy = IDENTITY)
    private String id;
    private String prdName;
    private Long price;
    private String cat;
    private Long purchase;
    private float score;
    private String subcat;
    private String imgSrc;
    private String site;
    private String webUrl;
}
