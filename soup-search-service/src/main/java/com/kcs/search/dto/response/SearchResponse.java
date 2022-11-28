package com.kcs.search.dto.response;

import com.kcs.search.document.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class SearchResponse {
    private String title;
    private Page<Product> result;
}
