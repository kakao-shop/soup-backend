package com.example.soup.elastic.search.dto;

import com.example.soup.domain.entity.elasticsearch.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class SearchResponse {
    private String title;
    private Page<Product> result;
}
