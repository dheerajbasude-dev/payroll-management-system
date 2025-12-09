package com.dheeraj.payroll.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Payroll Management System API",
                version = "1.0.0",
                description = "Comprehensive API documentation for the Payroll Management System. "
                        + "This includes endpoints for employee management, payroll processing, "
                        + "financial summaries, and authentication.",
                contact = @Contact(
                        name = "Dheeraj Basude",
                        email = "dheerajbe123@gmail.com",
                        url = "https://www.dummy.com"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )

        ),
        tags = {
                @Tag(name = "Authentication", description = "Operations for Authentication"),
                @Tag(name = "Employee", description = "Operations for managing employees"),
                @Tag(name = "Payroll", description = "Payroll calculation and processing APIs"),
                @Tag(name = "Summary", description = "Reporting and analytics APIs")
        },
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = @Server(url = "http://localhost:8080")
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
@Configuration
public class SwaggerOpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)
                                        .name("Authorization")));
    }
}
