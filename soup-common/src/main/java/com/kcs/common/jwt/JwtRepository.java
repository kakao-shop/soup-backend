package com.kcs.common.jwt;

import com.kcs.common.entity.mysql.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface JwtRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberIdx(Long memberIdx);
}
