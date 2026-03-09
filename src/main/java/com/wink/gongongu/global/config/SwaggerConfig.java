package com.wink.gongongu.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        SecurityScheme bearerAuth = new SecurityScheme()
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearerAuth", bearerAuth))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("공공구 API 명세서")
            .description("""
                발급받은 엑세스토큰은 Authorization 헤더에 "Bearer ${accessToken}" 형태로 보내주세요.<br>
                테스트용 토큰 발급 API에서 미리 저장된 테스트 유저의 엑세스토큰을 발급받아서 테스트할 수 있습니다.<br>
                스웨거에서 테스트 시 하단의 Authorize 버튼 눌러서 엑세스토큰 값 복붙하면 자동으로 헤더에 담겨 전송됩니다.
                """)
            .version("v0");
    }
}
