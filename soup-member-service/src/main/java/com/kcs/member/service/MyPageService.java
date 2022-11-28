package com.kcs.member.service;

import com.kcs.member.dto.request.MyInfoUpdateRequest;
import com.kcs.member.dto.response.MyInfoFindResponse;
import com.kcs.member.repository.MemberRepository;
import common.entity.mysql.Member;
import common.exception.NoSuchMemberExistException;
import common.exception.PasswordConfirmException;
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
        return MyInfoFindResponse.from(findMember);
    }

    @Transactional
    public void updateMyInfo(Long memberIdx, MyInfoUpdateRequest myInfoUpdateRequest) {
        myInfoUpdateRequest.encryptPassword(myInfoUpdateRequest.getPassword());
        Member findMember = findMemberByMemberIdx(memberIdx);
        findMember.updateMember(myInfoUpdateRequest.getPassword());
    }

    @Transactional
    public void deleteMyInfo(Long memberIdx) {
        memberRepository.deleteByMemberIdx(memberIdx);
    }
}
