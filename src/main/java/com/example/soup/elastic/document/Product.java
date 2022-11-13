package com.example.soup.elastic.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Document(indexName = "product", createIndex = false)
@Setter
@Getter
@AllArgsConstructor
@Builder
@Setting(settingPath = "/elasticsearch/settings/settings.json")
@Mapping(mappingPath = "/elasticsearch/mappings/mappings.json")
public class Product implements Comparable<Product> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    @Override
    public int compareTo(Product product) {
        if (product.score < score) {
            return 1;
        } else if (product.score > score) {
            return -1;
        }
        return 0;
    }
}
