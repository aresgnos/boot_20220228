package com.example.boot_20220228;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.mongo.JacksonMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@SpringBootApplication

// 임의로 만들든 컨트롤러의 위치를 지정, 서비스의 위치
@ComponentScan(basePackages = { "com.example.controller", "com.example.service" })

// 세션 => 현재 로그인하고 있는 사람의 기록
@EnableMongoHttpSession(collectionName = "session", maxInactiveIntervalInSeconds = 1800)

public class Boot20220228Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220228Application.class, args);
	}

	// @Bean => 프로그램이 구동되기 전에 미리 만들어지는 객체
	// JacksonMongoSessionConverter obj = new JacksonMongoSessionConverter();
	@Bean
	public JacksonMongoSessionConverter mongoSessionConverter() {
		return new JacksonMongoSessionConverter();
	}
}
