package com.kcs.common.advice;

import com.kcs.common.dto.BaseResponse;
import com.kcs.common.dto.ErrorCode;
import com.kcs.common.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class JWTExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger("Exception logger");
    @ExceptionHandler(NoAccessTokenException.class)
    public ResponseEntity<BaseResponse> handleNoAccessToken() {
        logger.info(ErrorCode.NoAccessToken.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoAccessToken));
    }

    @ExceptionHandler(NoRefreshTokenException.class)
    public ResponseEntity<BaseResponse> handleNoRefreshToken() {
        logger.info(ErrorCode.NoRefreshToken.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoRefreshToken));
    }

    @ExceptionHandler({ExpiredJwtException.class, ExpiredAccessTokenException.class})
    public ResponseEntity<BaseResponse> handleExpiredAccessJwt() {
        logger.info(ErrorCode.ExpiredAccessJwt.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredAccessJwt));
    }

    @ExceptionHandler({JwtException.class, InvalidTokenException.class})
    public ResponseEntity<BaseResponse> handleJwtValidation() {
        logger.info(ErrorCode.JwtValidation.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.JwtValidation));
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<BaseResponse> handleExpiredRefreshJwt() {
        logger.info(ErrorCode.ExpiredRefreshJwt.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ExpiredRefreshJwt));
    }

}
