package com.example.boot_20220228;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

// 임의로 만들든 컨트롤러의 위치를 지정
@ComponentScan(basePackages = { "com.example.controller", "com.example.service" })
public class Boot20220228Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220228Application.class, args);
	}

}
