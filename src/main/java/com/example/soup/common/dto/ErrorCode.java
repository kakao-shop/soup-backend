package com.example.soup.common.dto;

import com.example.soup.common.Exception.PasswordConfirmException;
import lombok.Getter;

@Getter
public enum ErrorCode {

    // common error
    IllegalArguement(4000,"입력값이 올바르지 않습니다."),

    // member error
    IdAlreadyExist(4001,"사용 중인 ID입니다."),
    IllegalBirthday(4001,"생년월일이 올바르지 않습니다."),
    PasswordConfirm(4001,"비밀번호가 일치하지 않습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
