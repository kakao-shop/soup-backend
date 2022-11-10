package com.example.soup.domain.entity.mariadb;

import com.example.soup.domain.entity.mariadb.Theme;
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

    private String mainCategory;

    private String subCategory;

    @ManyToOne
    @JoinColumn(name = "theme_idx")
    private Theme theme;

}
