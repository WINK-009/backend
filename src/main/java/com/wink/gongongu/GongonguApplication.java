package com.wink.gongongu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GongonguApplication {

	public static void main(String[] args) {
		SpringApplication.run(GongonguApplication.class, args);
	}

}
