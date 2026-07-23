package br.com.ifba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Classe principal responsável por inicializar a aplicação Spring Boot do TODO ESPORTE.
 */
@SpringBootApplication
// Ativa o suporte de serialização estável para objetos de Paginação (PageImpl)
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class TodoEsporteApplication {

    public static void main(String[] args) {
        // Inicializa o servidor web e carrega todos os componentes (Controllers, Services, etc.)
        SpringApplication.run(TodoEsporteApplication.class, args);
    }
}