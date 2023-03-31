package com.example.hana.Config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Todo API Document")
                .version("v1.0.0")
                .description("Todo API 명세서");
        String jwtSchemeName = "X-AUTH-TOKEN";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(HttpHeaders.AUTHORIZATION)
                        .in(SecurityScheme.In.HEADER)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI().info(info).addSecurityItem(securityRequirement).components(components);    }
}

//.addServersItem(new io.swagger.v3.oas.models.servers.Server().url("/"))

//        return new OpenAPI().addServersItem(new Server().url("/")).components(new Components().addSecuritySchemes("bassicScheme",
//                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
//                .info(new Info().title("springdoc API").version("V1")
//                        .license(new License().name("Apache 2.0").url("<http://springdoc.org>")));