package com.kcs.member.dto.request;

import common.entity.constant.Gender;
import common.entity.constant.Oauth;
import common.entity.constant.Role;
import common.entity.mysql.Member;
import common.exception.PasswordConfirmException;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberCreateRequest {

    @Size(min = 5, max = 12, message = "ID 입력(5~12자)")
    private String id;

    @Size(min = 2, max = 10, message = "닉네임 입력(2~10자)")
    private String nickname;

    @Size(min = 6, max = 15, message = "비밀번호 입력(6~15자)")
    private String password;

    private String confirmPassword;

    private String birthday;

    private String gender;

    private String role;

    private String oauth;

    public void encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public void confirmPassword() {
        if (!password.equals(confirmPassword))
            throw new PasswordConfirmException();
    }

    public Member toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Member.builder()
                .id(id)
                .nickname(nickname)
                .password(password)
                .birthday(LocalDate.parse(birthday, formatter))
                .gender(Gender.valueOf(gender))
                .role(Role.valueOf(role))
                .oauth(Oauth.valueOf(oauth))
                .totalAccessCnt(0L)
                .build();
    }

}
