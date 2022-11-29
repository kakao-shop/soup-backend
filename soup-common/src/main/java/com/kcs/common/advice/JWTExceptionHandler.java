package com.kcs.common.advice;

import com.kcs.common.dto.BaseResponse;
import com.kcs.common.dto.ErrorCode;
import com.kcs.common.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class JWTExceptionHandler {

    @ExceptionHandler(NoAccessTokenException.class)
    public ResponseEntity<BaseResponse> handleNoAccessToken() {
        log.info(ErrorCode.NoAccessToken.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoAccessToken));
    }

    @ExceptionHandler(NoRefreshTokenException.class)
    public ResponseEntity<BaseResponse> handleNoRefreshToken() {
        log.info(ErrorCode.NoRefreshToken.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoRefreshToken));
    }

    @ExceptionHandler({ExpiredJwtException.class, ExpiredAccessTokenException.class})
    public ResponseEntity<BaseResponse> handleExpiredAccessJwt() {
        log.info(ErrorCode.ExpiredAccessJwt.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredAccessJwt));
    }

    @ExceptionHandler({JwtException.class, InvalidTokenException.class})
    public ResponseEntity<BaseResponse> handleJwtValidation() {
        log.info(ErrorCode.JwtValidation.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.JwtValidation));
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<BaseResponse> handleExpiredRefreshJwt() {
        log.info(ErrorCode.ExpiredRefreshJwt.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredRefreshJwt));
    }

}
