package com.kcs.soup.api.admin;

import com.kcs.soup.entity.mysql.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Page<Theme> findAll(Pageable pageable);
}
