package com.kcs.search.repository.jpa;

import com.kcs.common.entity.mysql.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Page<Theme> findAll(Pageable pageable);

}
