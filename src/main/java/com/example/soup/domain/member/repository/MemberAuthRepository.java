package com.example.soup.domain.member.repository;

import com.example.soup.domain.entity.redis.MemberTokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface MemberAuthRepository extends CrudRepository<MemberTokenInfo, String> {
}