package com.kcs.search;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;


@SpringBootApplication(scanBasePackages = {"com.kcs.search", "com.kcs.common"})
@EnableJpaRepositories(basePackages = {"com.kcs.common", "com.kcs.search.repository.jpa"})
@EntityScan(basePackages = {"com.kcs.common"})
@EnableScheduling
public class SearchApplication {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("현재 시간: "+ new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
