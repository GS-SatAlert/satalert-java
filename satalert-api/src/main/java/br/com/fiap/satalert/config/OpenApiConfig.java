package br.com.fiap.satalert.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SatAlert API",
                version = "1.0.0",
                description = "Sistema de monitoramento de queimadas e desmatamento via satélite — FIAP Global Solution 2026/1",
                contact = @Contact(name = "Equipe SatAlert", email = "satalert@fiap.com")
        ),
        servers = {
                @Server(url = "https://satalert-java-production.up.railway.app", description = "Railway (Produção)"),
                @Server(url = "http://localhost:8080", description = "Local")
        },
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Insira o token JWT obtido no endpoint /api/auth/login"
)
public class OpenApiConfig {}