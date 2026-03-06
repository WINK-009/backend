package com.wink.gongongu;

import com.wink.gongongu.global.props.S3Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(S3Properties.class)
public class GongonguApplication {

	public static void main(String[] args) {
		SpringApplication.run(GongonguApplication.class, args);
	}

}
