package com.kcs.soup.api.search.dto;

import com.kcs.soup.api.search.document.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class SearchResponse {
    private String title;
    private Page<Product> result;
}
