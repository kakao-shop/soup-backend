package com.example.soup.admin;

import com.example.soup.admin.dto.MemberDto;
import com.example.soup.domain.Member;
import com.example.soup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    public Page<Member> findMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members;
    }
}
