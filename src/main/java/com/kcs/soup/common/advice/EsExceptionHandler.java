package com.kcs.soup.common.advice;

import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.dto.ErrorCode;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EsExceptionHandler {

    @ExceptionHandler(ElasticsearchStatusException.class)
    public ResponseEntity<BaseResponse> handleEsStatus() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.ElasticsearchStatus));
    }
}
