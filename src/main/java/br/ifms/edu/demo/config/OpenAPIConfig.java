package br.ifms.edu.demo.config; // (Exemplo de pacote)

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API da Biblioteca")
                        .version("1.0")
                        .description("Documentação da API para o sistema de biblioteca."));
    }
}
