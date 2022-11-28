package com.kcs.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class PasswordCheckRequest {
    @Size(min = 6, max = 15, message = "비밀번호 입력(6~15자)")
    private String password;
}
