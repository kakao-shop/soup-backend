package com.example.soup.member;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createMember(
            @Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        memberCreateRequest.confirmPassword();
        memberService.createMember(memberCreateRequest);
        return ResponseEntity.ok().body(new BaseResponse(200,"회원 가입에 성공했습니다."));
    }

    @GetMapping("id-check")
    public ResponseEntity<BaseResponse> validateDuplicatedId(
            @RequestParam String id){
        if (id.isBlank())
            throw new IllegalArgumentException();
        memberService.validateDuplicatedId(id);
        return ResponseEntity.ok().body(new BaseResponse(200, "아이디가 중복되지 않습니다."));
    }

}
