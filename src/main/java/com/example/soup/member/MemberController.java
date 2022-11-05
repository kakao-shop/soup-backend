package com.example.soup.member;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signup(
            @Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        memberCreateRequest.confirmPassword();
        memberService.createMember(memberCreateRequest);
        return ResponseEntity.ok().body(new BaseResponse(200,"회원 가입에 성공했습니다."));
    }

}
