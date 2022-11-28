package com.kcs.search.dto.response;

import com.kcs.search.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubcatSearchResponse {
    private List<SearchDto> searchDtoList;
}
