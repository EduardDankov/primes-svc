package dev.dankov.primes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig
{
    private static final String BEARER_TOKEN = "Bearer <token>";

    @Bean
    public OpenAPI openAPI(@Value("${spring.application.name}") String appName)
    {
        return new OpenAPI()
            .components(buildComponents())
            .info(buildInfo(appName))
            .security(List.of(new SecurityRequirement().addList(BEARER_TOKEN)));
    }

    private Components buildComponents()
    {
        return new Components()
            .addSecuritySchemes(BEARER_TOKEN, new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Authorization: Bearer <token>"));
    }

    private Info buildInfo(String appName)
    {
        return new Info()
            .title(appName)
            .description("Description")
            .contact(new Contact().name("Name").email("Email"))
            .version("1.0");
    }
}
