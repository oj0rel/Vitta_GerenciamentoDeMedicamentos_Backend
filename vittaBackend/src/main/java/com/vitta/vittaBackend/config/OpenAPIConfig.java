package com.vitta.vittaBackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        // Define as informações gerais da sua API
        Info info = new Info()
                .title("API do Projeto Vitta")
                .version("1.0")
                .description("API para gestão pessoal de medicamentos, auxiliando usuários no controle de horários, dosagens e histórico de uso.");

        // Define o esquema de segurança (JWT Bearer Token)
        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Insira o token JWT aqui para autenticar. Exemplo: 'Bearer eyJhbGciOi...'");

        // Adiciona o esquema de segurança aos componentes da documentação
        Components components = new Components()
                .addSecuritySchemes(securitySchemeName, securityScheme);

        // Adiciona o requisito de segurança (o cadeado) a todos os endpoints
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}
