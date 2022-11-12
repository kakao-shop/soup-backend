package com.example.soup.elastic.controller;

import com.example.soup.api.member.jwt.JwtTokenProvider;
import com.example.soup.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public ResponseEntity<BaseResponse> searchMain(){
        Long memberIdx = jwtTokenProvider.getMemberIdxIfLogined();
        if(memberIdx == null){
            // 오늘의 추천
        }else{
            // 사용자 로그 기반의 추천
        }
        return ResponseEntity.ok(new BaseResponse(200, "성공"));
    }
}
