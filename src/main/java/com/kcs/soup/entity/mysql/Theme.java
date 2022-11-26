package com.kcs.soup.entity.mysql;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kcs.soup.api.admin.dto.ThemeFindResponse;
import com.kcs.soup.api.search.dto.BotThemeListResponse;
import com.kcs.soup.api.search.dto.MainThemeResponse;
import com.kcs.soup.entity.BaseCreatedTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theme extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50)
    private String title;

    @JsonBackReference
    @OneToMany(
            mappedBy = "theme",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ThemeCategory> categories;

    @JsonBackReference
    @OneToOne(
            mappedBy = "theme",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Banner banner;

    public BotThemeListResponse toBotThemeListDto() {
        return BotThemeListResponse.builder()
                .value(String.valueOf(idx))
                .label(title)
                .trigger("last")
                .build();
    }

    public static Theme from(String title) {
        return Theme.builder()
                .title(title)
                .build();
    }

    public ThemeFindResponse toThemeFindResDto() {
        return ThemeFindResponse.builder()
                .title(this.title)
                .categoryList(
                        this.categories
                                .stream()
                                .map(ThemeCategory::toThemeCategoryDto)
                                .collect(Collectors.toList()))
                .banner(this.banner.toBannerDto())
                .build();
    }

    public MainThemeResponse toMainThemeResDto() {
        return MainThemeResponse.builder()
                .idx(this.idx)
                .title(this.title)
                .banner(this.banner.getPath())
                .build();
    }
}
