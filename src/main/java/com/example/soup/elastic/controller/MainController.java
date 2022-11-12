package com.example.soup.elastic.controller;

import com.example.soup.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public ResponseEntity<BaseResponse> searchMain(){

        return ResponseEntity.ok(new BaseResponse(200, "성공"));
    }
}
