package com.kcs.soup.api.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class RankResponse {
    private String rank;
    private List<RankDto> result;
    private String startTime;
    private String endTime;

    public RankResponse(List<RankDto> rankDtoList, String rank) {
        this.result = rankDtoList;
        this.rank = rank;
        this.startTime = LocalDateTime.now().minusHours(24).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
