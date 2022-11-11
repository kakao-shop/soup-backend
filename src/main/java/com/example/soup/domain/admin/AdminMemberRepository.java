package com.example.soup.domain.admin;

import com.example.soup.domain.entity.mariadb.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<Member,Long> {
    Page<Member> findAll(Pageable pageable);
}
