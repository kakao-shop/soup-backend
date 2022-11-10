package com.example.soup.elasticsearch;

import com.example.soup.search.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@Document(indexName = "product-2022-11-10-21-30")
public class Product {
    @Id
    private String id;

    @Field(type = FieldType.Float)
    private Float score;

    @Field(type = FieldType.Text)
    private String site;

    @Field(type = FieldType.Text)
    private String prdName;

    @Field(type = FieldType.Text)
    private String webUrl;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(type = FieldType.Text)
    private String cat;

    @Field(type = FieldType.Long)
    private Long purchase;

    @Field(type = FieldType.Text)
    private String subcat;

    @Field(type = FieldType.Text)
    private String imgSrc;

}
