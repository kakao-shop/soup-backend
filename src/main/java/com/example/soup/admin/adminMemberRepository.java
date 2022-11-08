package com.example.soup.admin;

import com.example.soup.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface adminMemberRepository extends JpaRepository<Member,Long> {
    Page<Member> findAll(Pageable pageable);
}
