package com.kcs.admin.reporitory;

import common.entity.mysql.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
