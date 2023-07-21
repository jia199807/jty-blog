package com.jty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jty.mapper")
@SpringBootApplication
public class JtyBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JtyBlogApplication.class, args);
	}

}
