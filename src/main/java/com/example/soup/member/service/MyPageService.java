package com.example.soup.member.service;

import com.example.soup.common.exceptions.NoSuchMemberExistException;
import com.example.soup.common.exceptions.PasswordConfirmException;
import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.member.dto.request.MyInfoUpdateRequest;
import com.example.soup.member.dto.response.MyInfoFindResponse;
import com.example.soup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;

    public void checkPassword(long memberIdx, String password) {
        Member findMember = findMemberByMemberIdx(memberIdx);
        if (findMember.isSamePassword(password)) {
            throw new PasswordConfirmException();
        }
    }

    public Member findMemberByMemberIdx(Long memberIdx) {
        return memberRepository.findByMemberIdx(memberIdx)
                .orElseThrow(NoSuchMemberExistException::new);
    }

    public MyInfoFindResponse findMyInfo(Long memberIdx) {
        Member findMember = findMemberByMemberIdx(memberIdx);
        return findMember.toMyInfoFindResponseDto();
    }

    @Transactional
    public void updateMyInfo(Long memberIdx, MyInfoUpdateRequest myInfoUpdateRequest) {
        myInfoUpdateRequest.encryptPassword(myInfoUpdateRequest.getPassword());
        Member findMember = findMemberByMemberIdx(memberIdx);
        findMember.updateMember(myInfoUpdateRequest);
    }

    @Transactional
    public void deleteMyInfo(Long memberIdx) {
        memberRepository.deleteByMemberIdx(memberIdx);
    }
}
