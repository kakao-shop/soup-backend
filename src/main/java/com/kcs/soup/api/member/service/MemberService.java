package com.kcs.soup.api.member.service;

import com.kcs.soup.common.exceptions.IdAlreadyExistException;
import com.kcs.soup.entity.mysql.Member;
import com.kcs.soup.api.member.dto.request.MemberCreateRequest;
import com.kcs.soup.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(MemberCreateRequest memberCreateRequest) {
        validateDuplicatedId(memberCreateRequest.getId());
        memberCreateRequest.encryptPassword(memberCreateRequest.getPassword());
        memberRepository.save(memberCreateRequest.toEntity());
    }

    public void validateDuplicatedId(String id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            throw new IdAlreadyExistException();
        }
    }

}
