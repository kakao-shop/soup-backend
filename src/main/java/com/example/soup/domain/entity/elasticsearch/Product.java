package com.example.soup.domain.entity.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Document(indexName = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String prdName;
    private Long price;
    private String category;
    private String site;
    private Long count;
    private String keyword;
    private Date crawlDate;
    private String imgUrl;
    private String prdSite;

}
