package com.example.soup.domain.member.controller;

import com.example.soup.domain.common.dto.BaseResponse;
import com.example.soup.domain.common.exceptions.NoAccessTokenException;
import com.example.soup.domain.member.service.CookieTokenProvider;
import com.example.soup.domain.member.service.MemberAuthService;
import com.example.soup.domain.member.service.MemberService;
import com.example.soup.domain.member.dto.TokenDto;
import com.example.soup.domain.member.dto.request.LoginRequest;
import com.example.soup.domain.member.dto.request.MemberCreateRequest;
import com.example.soup.domain.member.dto.response.LoginResponse;
import com.example.soup.domain.member.dto.response.TokenResponse;
import com.example.soup.domain.member.jwt.JwtTokenProvider;
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

    private final CookieTokenProvider cookieTokenProvider;
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
        cookieTokenProvider.set(response, loginResponse.getRefreshToken());
        loginResponse.setRefreshTokenNull();
        return ResponseEntity.ok(new BaseResponse<>(200, "로그인에 성공했습니다.", loginResponse));
    }

    // token 재발급
    @GetMapping("/refresh-token")
    public ResponseEntity<BaseResponse> generateToken(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie,
            HttpServletResponse response) {
        String accessToken = jwtTokenProvider.getJwt();
        if (accessToken == null)
            throw new NoAccessTokenException();
        TokenDto tokenDto = memberAuthService.createToken(cookie, accessToken);
        cookieTokenProvider.set(response, tokenDto.getRefreshToken());
        TokenResponse tokenResponse = new TokenResponse(tokenDto.getAccessToken());
        return ResponseEntity.ok(new BaseResponse<>(200, "토큰 재발급에 성공했습니다.", tokenResponse));
    }

    // logout
    @DeleteMapping("/logout")
    public ResponseEntity<BaseResponse> logout(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie) {
        memberAuthService.deleteToken(cookie);
        return ResponseEntity.ok(new BaseResponse<>(200, "로그아웃에 성공했습니다."));
    }

}