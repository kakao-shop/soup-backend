package com.kcs.soup.api.admin.reporitory;

import com.kcs.soup.entity.mysql.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}