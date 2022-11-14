package com.kcs.soup.elastic.dto;

import com.kcs.soup.elastic.document.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class SearchResponse {
    private String title;
    private Page<Product> result;
}
