package com.kcs.common.advice;

import com.kcs.common.dto.BaseResponse;
import com.kcs.common.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DatabaseExceptionHandler {

    @ExceptionHandler(ElasticsearchStatusException.class)
    public ResponseEntity<BaseResponse> handleEsStatus(Exception e) {
        log.error(ErrorCode.ElasticsearchStatus.getMessage());
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ElasticsearchStatus));
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<BaseResponse> handleRedisStatus(Exception e) {
        log.error(ErrorCode.RedisStatus.getMessage());
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.RedisStatus));
    }
}
