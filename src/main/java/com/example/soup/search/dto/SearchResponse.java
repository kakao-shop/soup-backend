package com.example.soup.search.dto;

import com.example.soup.elasticsearch.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class SearchResponse {
    private String title;
    private Page<Product> result;
}
