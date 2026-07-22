package br.com.ifba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal responsável por inicializar a aplicação Spring Boot do TODO ESPORTE.
 */
@SpringBootApplication
public class TodoEsporteApplication {

    public static void main(String[] args) {
        // Inicializa o servidor web e carrega todos os componentes (Controllers, Services, etc.)
        SpringApplication.run(TodoEsporteApplication.class, args);
    }
}