package com.wink.gongongu.auth.jwt.config;

import com.wink.gongongu.auth.jwt.probs.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

}
