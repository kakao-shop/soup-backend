package com.example.soup.member.service;

import com.example.soup.common.exceptions.IdAlreadyExistException;
import com.example.soup.domain.entity.Member;
import com.example.soup.member.dto.request.MemberCreateRequest;
import com.example.soup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
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
