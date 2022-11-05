package com.example.soup.member.dto;

import com.example.soup.common.Exception.PasswordConfirmException;
import com.example.soup.domain.Gender;
import com.example.soup.domain.Member;
import com.example.soup.domain.Oauth;
import com.example.soup.domain.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class MemberCreateRequest {

    @Length(min=5,max=12,message = "ID 입력(5~12자)")
    private String id;

    @Length(min=2,max=10, message = "닉네임 입력(2~10자)")
    private String nickname;

    @Length(min=6,max=15, message = "비밀번호 입력(6~15자)")
    private String password;

    private String confirmPassword;

    private String birthday;

    private String gender;

    private String role;

    private String oauth;

    public void confirmPassword(){
        if(!password.equals(confirmPassword))
            throw new PasswordConfirmException();
    }

    public Member toEntity(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Member.builder()
                .id(id)
                .nickname(nickname)
                .password(password)
                .birthday(LocalDate.parse(birthday,formatter))
                .gender(Gender.valueOf(gender))
                .role(Role.valueOf(role))
                .oauth(Oauth.valueOf(oauth))
                .build();

    }

}
