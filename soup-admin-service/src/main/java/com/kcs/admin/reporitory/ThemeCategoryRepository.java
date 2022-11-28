package com.kcs.admin.reporitory;

import common.entity.mysql.ThemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeCategoryRepository extends JpaRepository<ThemeCategory, Long> {

}
