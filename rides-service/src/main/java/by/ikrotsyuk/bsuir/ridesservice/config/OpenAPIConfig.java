package by.ikrotsyuk.bsuir.ridesservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8083"),
                        new Server().url("http://localhost:8086")))
                .info(new Info().title("Rides Service API").version("1.0.0"));
    }
}