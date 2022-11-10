package com.example.soup.search;

import com.example.soup.domain.entity.mariadb.ThemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeCatRepository extends JpaRepository<ThemeCategory,Long> {

    List<ThemeCategory> findByThemeIdx(Long themeIdx);
}
