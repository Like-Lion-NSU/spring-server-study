package com.example.hana;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "http://localhost:8080")
        }
)
@SpringBootApplication
public class HanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanaApplication.class, args);
    }

}
