package com.kcs.soup.api.member.repository;

import com.kcs.soup.api.entity.redis.MemberTokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface MemberAuthRepository extends CrudRepository<MemberTokenInfo, String> {
}