package com.kcs.soup.entity.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kcs.soup.api.admin.dto.ThemeCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 20)
    private String mainCategory;

    @Column(length = 20)
    private String subCategory;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Theme theme;

    public static ThemeCategory of(ThemeCategoryDto themeCategoryDto, Theme theme) {
        return ThemeCategory.builder()
                .mainCategory(themeCategoryDto.getMainCategory())
                .subCategory(themeCategoryDto.getSubCategory())
                .theme(theme)
                .build();
    }

    public ThemeCategoryDto toThemeCategoryDto(){
        return ThemeCategoryDto.builder()
                .mainCategory(this.mainCategory)
                .subCategory(this.subCategory)
                .build();
    }

}
