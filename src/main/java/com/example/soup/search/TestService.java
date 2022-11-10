package com.example.soup.search;

import org.springframework.beans.factory.annotation.Value;

public class TestService {

    @Value("${spring.elasticsearch.host}")
    private String elasticHost;

    public void test(){
        System.out.println("elasticHost: " + elasticHost);
    }
}
