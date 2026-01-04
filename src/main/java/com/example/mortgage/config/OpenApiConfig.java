package com.example.mortgage.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI mortgageOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mortgage Service API")
                        .description("APIs for mortgage interest rates and eligibility check")
                        .version("v1.0"));
    }
}
