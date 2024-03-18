package com.example.demo.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "demo 서비스 API 명세서",
                description = "스프링부트 demo 서비스 CRUD 실습 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("demo 서비스 API v1")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI api() {
        SecurityScheme apiKey = new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .in(SecurityScheme.In.HEADER)
            .name("X-ACCESS-TOKEN");

        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("X-ACCESS-TOKEN");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("X-ACCESS-TOKEN", apiKey))
            .addSecurityItem(securityRequirement);
    }
}
