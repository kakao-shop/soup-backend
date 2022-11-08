package com.example.soup.member.dto.request;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
public class MyInfoUpdateRequest {
    @Size(min = 6, max = 15, message = "비밀번호 입력(6~15자)")
    private String password;

    @Size(min = 2, max = 10, message = "닉네임 입력(2~10자)")
    private String nickname;

    public void encryptPassword(String BCryptpassword) {
        this.password = BCryptpassword;
    }
}
