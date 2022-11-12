package com.example.soup.elastic.dto;

import com.example.soup.elastic.document.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchDto {
    private String id;

    private String site;

    private String prdName;

    private Long price;

    private String cat;

    private Long purchase;

    private String subcat;

    private String imgSrc;

}
