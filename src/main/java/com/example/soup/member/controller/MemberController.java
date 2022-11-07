package com.example.soup.member.controller;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.member.jwt.JwtTokenProvider;
import com.example.soup.member.dto.*;
import com.example.soup.member.service.CookieTokenProvider;
import com.example.soup.member.service.MemberAuthService;
import com.example.soup.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final CookieTokenProvider cookieTokenProvider;
    private final MemberService memberService;
    private final MemberAuthService memberAuthService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createMember(
            @Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        memberCreateRequest.confirmPassword();
        memberService.createMember(memberCreateRequest);
        return ResponseEntity.ok(new BaseResponse(200, "회원 가입에 성공했습니다."));
    }

    @GetMapping("id-check")
    public ResponseEntity<BaseResponse> validateDuplicatedId(
            @RequestParam String id) {
        if (id.isBlank())
            throw new IllegalArgumentException();
        memberService.validateDuplicatedId(id);
        return ResponseEntity.ok(new BaseResponse(200, "아이디가 중복되지 않습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest request,
                                                 HttpServletResponse response) {
        System.out.println(request.getId());
        LoginResponse loginResponse = memberService.login(request);
        cookieTokenProvider.set(response, loginResponse.getRefreshToken());
        loginResponse.setRefreshTokenNull();
        return ResponseEntity.ok(new BaseResponse(200,"로그인에 성공했습니다.",loginResponse));
    }

    // token 재발급
    @GetMapping("/refresh-token")
    public ResponseEntity<BaseResponse> generateToken(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie,
            HttpServletRequest request,
            HttpServletResponse response) {
        String accessToken = jwtTokenProvider.getJwt();
        TokenDto tokenDto = memberAuthService.createToken(cookie, accessToken);
        cookieTokenProvider.set(response, tokenDto.getRefreshToken());
        RefreshAccessTokenResponse refreshAccessTokenResponse = new RefreshAccessTokenResponse(tokenDto.getAccessToken());
        return ResponseEntity.ok(new BaseResponse(200, "토큰 재발급에 성공했습니다.", refreshAccessTokenResponse));
    }

    // logout
    @DeleteMapping("/logout")
    public ResponseEntity<BaseResponse> logout(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie,
            HttpServletResponse response) {
        memberAuthService.deleteToken(cookie);
        cookieTokenProvider.delete(response, memberAuthService.getRefreshToken(cookie));
        return ResponseEntity.ok(new BaseResponse(200, "로그아웃에 성공했습니다."));
    }

}
