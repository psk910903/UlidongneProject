package com.study.UlidongneProject;

import com.study.UlidongneProject.service.Service1;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@RequiredArgsConstructor
public class UlidongneProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(UlidongneProjectApplication.class, args);
	}
}
