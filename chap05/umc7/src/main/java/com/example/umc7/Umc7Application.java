package com.example.umc7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Umc7Application {

	public static void main(String[] args) {
		SpringApplication.run(Umc7Application.class, args);
	}

}
