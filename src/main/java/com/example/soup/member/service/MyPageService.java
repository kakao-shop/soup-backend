package com.example.soup.member.service;

import com.example.soup.common.exceptions.NoSuchMemberExistException;
import com.example.soup.common.exceptions.PasswordConfirmException;
import com.example.soup.domain.entity.Member;
import com.example.soup.member.dto.request.MyInfoUpdateRequest;
import com.example.soup.member.dto.response.MyInfoFindResponse;
import com.example.soup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public void checkPassword(long memberIdx, String password) {
        Member findMember = findMemberByMemberIdx(memberIdx);
        if (!encoder.matches(password, findMember.getPassword())) {
            throw new PasswordConfirmException();
        }
    }

    public Member findMemberByMemberIdx(Long memberIdx) {
        Member findMember = memberRepository.findByMemberIdx(memberIdx)
                .orElseThrow(NoSuchMemberExistException::new);
        return findMember;
    }

    public MyInfoFindResponse findMyInfo(Long memberIdx) {
        Member findMember = findMemberByMemberIdx(memberIdx);
        return findMember.toMyInfoFindResponseDto();
    }

    public void updateMyInfo(Long memberIdx, MyInfoUpdateRequest myInfoUpdateRequest) {
        Member findMember = findMemberByMemberIdx(memberIdx);
        findMember.updateMember(myInfoUpdateRequest);
    }
}
