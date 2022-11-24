package com.kcs.soup.api.search.dto;

import com.kcs.soup.api.search.document.Product;
import com.kcs.soup.api.search.document.SelectItemLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SelectItemResponse {
    private String recently;
    private List<SelectItemLog> result;
}
