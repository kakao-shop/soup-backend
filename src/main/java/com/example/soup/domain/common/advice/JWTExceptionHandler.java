package com.example.soup.domain.common.advice;

import com.example.soup.domain.common.dto.BaseResponse;
import com.example.soup.domain.common.dto.ErrorCode;
import com.example.soup.domain.common.exceptions.InvalidTokenException;
import com.example.soup.domain.common.exceptions.NoAccessTokenException;
import com.example.soup.domain.common.exceptions.NoRefreshTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JWTExceptionHandler {

    @ExceptionHandler(NoAccessTokenException.class)
    public ResponseEntity<BaseResponse> handleNoAccessToken() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoAccessToken));
    }

    @ExceptionHandler(NoRefreshTokenException.class)
    public ResponseEntity<BaseResponse> handleNoRefreshToken() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoRefreshToken));
    }

    @ExceptionHandler({ExpiredJwtException.class,ExpiredJwtException.class})
    public ResponseEntity<BaseResponse> handleExpiredJwt() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredJwt));
    }

    @ExceptionHandler({JwtException.class, InvalidTokenException.class})
    public ResponseEntity<BaseResponse> handleJwtValidation() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.JwtValidation));
    }





}
