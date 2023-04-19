package com.study.UlidongneProject;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
@RequiredArgsConstructor
public class UlidongneProjectApplication {
	public static void main(String[] args) {
//		SpringApplication.run(UlidongneProjectApplication.class, args);
		SpringApplication application = new SpringApplication(UlidongneProjectApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}
}
