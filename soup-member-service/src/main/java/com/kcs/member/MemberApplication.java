package com.kcs.member;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;


@SpringBootApplication(scanBasePackages = {"com.kcs.member", "com.kcs.common"})
@EnableJpaRepositories(basePackages = {"com.kcs.member", "com.kcs.common"})
@EntityScan(basePackages = {"com.kcs.common"})
public class MemberApplication {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("현재 시간: "+ new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}
