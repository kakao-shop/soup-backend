package com.kcs.soup.api.member.service;

import com.kcs.soup.common.exceptions.NoSuchMemberExistException;
import com.kcs.soup.common.exceptions.PasswordConfirmException;
import com.kcs.soup.common.entity.mysql.Member;
import com.kcs.soup.api.member.dto.request.MyInfoUpdateRequest;
import com.kcs.soup.api.member.dto.response.MyInfoFindResponse;
import com.kcs.soup.api.member.repository.MemberRepository;
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
