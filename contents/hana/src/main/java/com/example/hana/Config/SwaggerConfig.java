package com.example.hana.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Todo API Document")
                .version("v1.0.0")
                .description("Todo API 명세서");
        return new OpenAPI().info(info);
    }
    //http://localhost:8080/swagger-ui/index.html
}
