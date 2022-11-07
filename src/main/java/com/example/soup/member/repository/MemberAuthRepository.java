package com.example.soup.member.repository;

import com.example.soup.domain.MemberTokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface MemberAuthRepository extends CrudRepository<MemberTokenInfo, String> {
}
