package com.example.soup.api.admin;

import com.example.soup.api.entity.mariadb.ThemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeCategoryRepository extends JpaRepository<ThemeCategory, Long> {
    List<ThemeCategory> findByThemeIdx(Long themeIdx);

    void deleteAllByThemeIdx(Long themeIdx);
}
