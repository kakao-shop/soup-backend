package com.example.soup.member;

import com.example.soup.common.Exception.IdAlreadyExistException;
import com.example.soup.domain.Member;
import com.example.soup.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder encoder;

    private final MemberRepository memberRepository;

    public Member createMember(MemberCreateRequest memberCreateRequest){
        validateDuplicateId(memberCreateRequest.getId());
        memberCreateRequest.setPassword(encoder.encode(memberCreateRequest.getPassword()));
        return memberRepository.save(memberCreateRequest.toEntity());
    }

    public void validateDuplicateId(String id){
        Member findMember = memberRepository.findById(id);
        if(findMember != null){
            throw new IdAlreadyExistException();
        }
    }
}
