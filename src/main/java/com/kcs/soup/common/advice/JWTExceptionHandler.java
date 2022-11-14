package com.kcs.soup.common.advice;

import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.dto.ErrorCode;
import com.kcs.soup.common.exceptions.ExpiredAccessTokenException;
import com.kcs.soup.common.exceptions.InvalidTokenException;
import com.kcs.soup.common.exceptions.NoAccessTokenException;
import com.kcs.soup.common.exceptions.NoRefreshTokenException;
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

    @ExceptionHandler({ExpiredJwtException.class, ExpiredAccessTokenException.class})
    public ResponseEntity<BaseResponse> handleExpiredJwt() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredJwt));
    }

    @ExceptionHandler({JwtException.class, InvalidTokenException.class})
    public ResponseEntity<BaseResponse> handleJwtValidation() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.JwtValidation));
    }


}
