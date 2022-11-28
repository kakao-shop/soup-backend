package com.kcs.search.dto.response;

import com.kcs.search.document.SelectItemLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SelectItemResponse {
    private String recently;
    private List<SelectItemLog> result;
}
