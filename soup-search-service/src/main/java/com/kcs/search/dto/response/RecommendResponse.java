package com.kcs.search.dto.response;

import com.kcs.search.document.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendResponse {
    private String recommend;
    private List<Product> result;
}
