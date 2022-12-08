package com.kcs.common.entity.mysql;


import com.kcs.common.entity.BaseTImeEntity;
import com.kcs.common.entity.constant.Gender;
import com.kcs.common.entity.constant.Oauth;
import com.kcs.common.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTImeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(length = 10)
    private String nickname;

    @Column(length = 20)
    private String id;

    private String password;

    @Column(length = 10)
    private String birthday;

    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private Oauth oauth;

    private Long totalAccessCnt;

    private LocalDateTime lastAccessTime;

    public void updateMember(String password) {
        this.password = password;
    }

    public void updateAccessInfo() {
        System.out.println("update 실행됨.");
        System.out.println("birthday: "+this.birthday);
        this.totalAccessCnt += 1;
        this.lastAccessTime = LocalDateTime.now();
    }

    public boolean isSamePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return !encoder.matches(password, this.password);
    }

}
