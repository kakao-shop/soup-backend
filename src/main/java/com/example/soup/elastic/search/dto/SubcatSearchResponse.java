package com.example.soup.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubcatSearchResponse {
    private List<SearchDto> searchDtoList;
}
