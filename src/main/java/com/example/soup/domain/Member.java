package com.example.soup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTImeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(length = 10)
    private String nickname;

    @Column(length = 20)
    private String id;

    private String password;

    private LocalDate birthday;

    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private Oauth oauth;

    @Builder
    public Member(String id, String nickname, String password,
                  LocalDate birthday, Gender gender, Role role, Oauth oauth) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.role = role;
        this.oauth = oauth;
    }
}
