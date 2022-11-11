package com.example.soup.domain.member.dto.request;

import com.example.soup.domain.common.exceptions.PasswordConfirmException;
import com.example.soup.domain.constant.Gender;
import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.domain.constant.Oauth;
import com.example.soup.domain.constant.Role;
import lombok.*;
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
                .build();
    }

}
