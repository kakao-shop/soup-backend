package com.example.soup.elastic.dto;

import com.example.soup.elastic.KeywordLog;
import com.example.soup.elastic.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendResponse {
    private String recommend;
    private List<Product> result;
}
