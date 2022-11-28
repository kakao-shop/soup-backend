package common.advice;

import common.dto.BaseResponse;
import common.dto.ErrorCode;
import common.exception.IdAlreadyExistException;
import common.exception.NoSuchMemberExistException;
import common.exception.NoSuchThemeExistException;
import common.exception.PasswordConfirmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdAlreadyExistException.class)
    public ResponseEntity<BaseResponse> handleIdAlreadyExist() {
        log.info(ErrorCode.IdAlreadyExist.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.IdAlreadyExist));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleNotValid(MethodArgumentNotValidException exception) {
        String validateMsg = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.info(validateMsg);
        return ResponseEntity.badRequest().body(new BaseResponse(4001, validateMsg));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArguement(Exception e) {
        log.warn(ErrorCode.IllegalArguement.getMessage());
        log.warn(e.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.IllegalArguement));
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<BaseResponse> handleIllegalBirthday() {
        log.info(ErrorCode.IllegalBirthday.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.IllegalBirthday));
    }

    @ExceptionHandler(PasswordConfirmException.class)
    public ResponseEntity<BaseResponse> handlePasswordConfirm() {
        log.info(ErrorCode.PasswordConfirm.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.PasswordConfirm));
    }

    @ExceptionHandler(NoSuchMemberExistException.class)
    public ResponseEntity<BaseResponse> handleNoSuchMemberExist() {
        log.info(ErrorCode.NoSuchMemberExist.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoSuchMemberExist));
    }

    @ExceptionHandler(NoSuchThemeExistException.class)
    public ResponseEntity<BaseResponse> handleNoSuchThemeExist() {
        log.info(ErrorCode.NoSuchThemeExist.getMessage());
        return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.NoSuchThemeExist));
    }
}
