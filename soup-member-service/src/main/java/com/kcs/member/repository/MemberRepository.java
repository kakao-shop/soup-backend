package com.kcs.member.repository;

import common.entity.mysql.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);

    Optional<Member> findByMemberIdx(Long memberIdx);

    void deleteByMemberIdx(Long memberIdx);
}
