package com.example.soup.admin;

import com.example.soup.domain.entity.mariadb.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
