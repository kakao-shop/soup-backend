package com.kcs.soup.common.advice;

import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.dto.ErrorCode;
import com.kcs.soup.common.exceptions.ExpiredAccessTokenException;
import com.kcs.soup.common.exceptions.InvalidTokenException;
import com.kcs.soup.common.exceptions.NoAccessTokenException;
import com.kcs.soup.common.exceptions.NoRefreshTokenException;
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
    public ResponseEntity<BaseResponse> handleExpiredJwt() {
        log.info(ErrorCode.ExpiredJwt.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredJwt));
    }

    @ExceptionHandler({JwtException.class, InvalidTokenException.class})
    public ResponseEntity<BaseResponse> handleJwtValidation() {
        log.info(ErrorCode.JwtValidation.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.JwtValidation));
    }


}
