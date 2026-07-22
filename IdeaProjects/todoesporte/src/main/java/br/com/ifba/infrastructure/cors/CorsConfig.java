package br.com.ifba.infrastructure.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Libera todos os endpoints da API
                        .allowedOrigins("*") // Permite requisicoes de qualquer origem (pode restringir ao dominio do frontend depois)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodos HTTP permitidos
                        .allowedHeaders("*"); // Permite todos os cabecalhos
            }
        };
    }
}