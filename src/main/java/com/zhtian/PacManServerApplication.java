package com.zhtian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PacManServerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PacManServerApplication.class);
		app.setWebEnvironment(true);
		app.run(args);
	}
}
