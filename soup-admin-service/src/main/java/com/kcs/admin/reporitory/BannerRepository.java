package com.kcs.admin.reporitory;

import com.kcs.common.entity.mysql.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
