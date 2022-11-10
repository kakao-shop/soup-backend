package com.example.soup.elastic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocsDTO {
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
