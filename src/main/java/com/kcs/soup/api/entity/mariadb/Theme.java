package com.kcs.soup.api.entity.mariadb;

import com.kcs.soup.api.entity.BaseCreatedTimeEntity;
import com.kcs.soup.elastic.dto.BotThemeListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theme extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;

    public BotThemeListResponse toBotThemeListDto() {
        return BotThemeListResponse.builder()
                .value(String.valueOf(idx))
                .label(title)
                .trigger("last")
                .build();
    }

}
