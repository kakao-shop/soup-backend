package com.example.soup.member.controller;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.member.dto.request.MemberCreateRequest;
import com.example.soup.member.dto.request.MyInfoUpdateRequest;
import com.example.soup.member.dto.response.MyInfoFindResponse;
import com.example.soup.member.service.MemberAuthService;
import com.example.soup.member.service.MyPageService;
import com.example.soup.member.support.TokenMemberIdx;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    private final MemberAuthService memberAuthService;

    @PostMapping("/mypage/password-check")
    public ResponseEntity<BaseResponse> checkPassword(@TokenMemberIdx Long memberIdx, @Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        myPageService.checkPassword(memberIdx, memberCreateRequest.getPassword());
        // 조회 합치기
        MyInfoFindResponse myInfoFindResponse = myPageService.findMyInfo(memberIdx);
        return ResponseEntity.ok(new BaseResponse<>(200, "비밀번호가 일치합니다.", myInfoFindResponse));
    }

//    @GetMapping("/mypage")
//    public ResponseEntity<BaseResponse> findMyInfo(@TokenMemberIdx Long memberIdx) {
//        MyInfoFindResponse myInfoFindResponse = myPageService.findMyInfo(memberIdx);
//        return ResponseEntity.ok(new BaseResponse<>(200, "나의 정보를 성공적으로 조회했습니다.", myInfoFindResponse));
//    }

    @PatchMapping("/mypage")
    public ResponseEntity<BaseResponse> updateMyInfo(@TokenMemberIdx Long memberIdx, @Valid @RequestBody MyInfoUpdateRequest myInfoUpdateRequest) {
        myPageService.updateMyInfo(memberIdx, myInfoUpdateRequest);
        return ResponseEntity.ok(new BaseResponse<>(200, "회원 정보가 수정되었습니다."));
    }

    @DeleteMapping("/mypage")
    public ResponseEntity<BaseResponse> deleteMyInfo(@TokenMemberIdx Long memberIdx,
                                                     @CookieValue(value = "refreshToken", required = false) Cookie cookie) {
        memberAuthService.deleteToken(cookie);
        myPageService.deleteMyInfo(memberIdx);
        return ResponseEntity.ok(new BaseResponse<>(200, "회원 정보가 삭제되었습니다."));
    }
}
