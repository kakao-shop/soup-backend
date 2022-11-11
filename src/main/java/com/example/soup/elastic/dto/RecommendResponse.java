package com.example.soup.elastic.dto;

import com.example.soup.elastic.document.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendResponse {
    private String recommend;
    private List<Product> result;
}
