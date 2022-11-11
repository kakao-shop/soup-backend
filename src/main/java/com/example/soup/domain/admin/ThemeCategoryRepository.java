package com.example.soup.domain.admin;

import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.domain.entity.mariadb.ThemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeCategoryRepository extends JpaRepository<ThemeCategory, Long> {
}
