package com.kcs.soup.api.member.dto.request;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Size;

@Getter
public class MyInfoUpdateRequest {
    @Size(min = 6, max = 15, message = "비밀번호 입력(6~15자)")
    private String password;

    public void encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }
}
