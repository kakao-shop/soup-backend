package com.kcs.soup.api.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubcatSearchResponse {
    private List<SearchDto> searchDtoList;
}
