package com.kcs.common.entity.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    public static ThemeCategory of(String mainCategory, String subCategory, Theme theme) {
        return ThemeCategory.builder()
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .theme(theme)
                .build();
    }


}
