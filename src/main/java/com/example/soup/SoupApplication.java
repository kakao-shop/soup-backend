package com.example.soup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.example.soup.elastic")
@EnableScheduling
@EnableJpaRepositories("com.example.soup.domain")
public class SoupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoupApplication.class, args);
	}

}
