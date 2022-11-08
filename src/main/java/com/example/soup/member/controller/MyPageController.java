package com.example.soup.member.controller;

import com.example.soup.common.dto.BaseResponse;
import com.example.soup.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PostMapping("/password-check")
    public ResponseEntity<BaseResponse> checkPassword(String password){
        myPageService.checkPassword(password);
        return ResponseEntity.ok(new BaseResponse(200,"비밀번호가 일치합니다."));
    }

}
