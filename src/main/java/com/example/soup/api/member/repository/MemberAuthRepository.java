package com.example.soup.api.member.repository;

import com.example.soup.api.entity.redis.MemberTokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface MemberAuthRepository extends CrudRepository<MemberTokenInfo, String> {
}
