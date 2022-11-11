package com.example.soup.domain.common.advice;

import com.example.soup.domain.common.dto.BaseResponse;
import com.example.soup.domain.common.dto.ErrorCode;
import com.example.soup.domain.common.exceptions.IdAlreadyExistException;
import com.example.soup.domain.common.exceptions.NoSuchMemberExistException;
import com.example.soup.domain.common.exceptions.NoSuchThemeExistException;
import com.example.soup.domain.common.exceptions.PasswordConfirmException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdAlreadyExistException.class)
    public ResponseEntity<BaseResponse> handleIdAlreadyExist() {
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.IdAlreadyExist));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleNotValid(MethodArgumentNotValidException exception){
        System.out.println(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new BaseResponse(4001,message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArguement(){
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.IllegalArguement));
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<BaseResponse> handleIllegalBirthday(){
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.IllegalBirthday));
    }

    @ExceptionHandler(PasswordConfirmException.class)
    public ResponseEntity<BaseResponse> handlePasswordConfirm(){
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.PasswordConfirm));
    }

    @ExceptionHandler(NoSuchMemberExistException.class)
    public ResponseEntity<BaseResponse> handleNoSuchMemberExist(){
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoSuchMemberExist));
    }

    @ExceptionHandler(NoSuchThemeExistException.class)
    public ResponseEntity<BaseResponse> handleNoSuchThemeExist(){
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoSuchThemeExist));
    }
}
