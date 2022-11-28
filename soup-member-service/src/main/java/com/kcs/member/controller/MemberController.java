package com.kcs.member.controller;

import com.kcs.member.dto.TokenDto;
import com.kcs.member.dto.request.LoginRequest;
import com.kcs.member.dto.request.MemberCreateRequest;
import com.kcs.member.dto.response.LoginResponse;
import com.kcs.member.dto.response.TokenResponse;
import com.kcs.member.service.MemberAuthService;
import com.kcs.member.service.MemberService;
import com.kcs.common.dto.BaseResponse;
import com.kcs.common.exception.NoAccessTokenException;
import com.kcs.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberAuthService memberAuthService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createMember(
            @Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        memberCreateRequest.confirmPassword();
        memberService.createMember(memberCreateRequest);
        return ResponseEntity.ok(new BaseResponse<>(200, "회원 가입에 성공했습니다."));
    }

    @GetMapping("id-check")
    public ResponseEntity<BaseResponse> validateDuplicatedId(
            @RequestParam String id) {
        if (id.isBlank())
            throw new IllegalArgumentException();
        memberService.validateDuplicatedId(id);
        return ResponseEntity.ok(new BaseResponse<>(200, "아이디가 중복되지 않습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        LoginResponse loginResponse = memberAuthService.login(request);
        loginResponse.setRefreshToken(loginResponse.getRefreshToken());
        return ResponseEntity.ok(new BaseResponse<>(200, "로그인에 성공했습니다.", loginResponse));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<BaseResponse> generateToken(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie) {
        String accessToken = jwtTokenProvider.getJwt();
        if (accessToken == null)
            throw new NoAccessTokenException();
        TokenDto tokenDto = memberAuthService.createToken(cookie, accessToken);
        TokenResponse tokenResponse = new TokenResponse(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return ResponseEntity.ok(new BaseResponse<>(200, "토큰 재발급에 성공했습니다.", tokenResponse));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<BaseResponse> logout(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie) {
        memberAuthService.deleteToken(cookie);
        return ResponseEntity.ok(new BaseResponse<>(200, "로그아웃에 성공했습니다."));
    }

}
