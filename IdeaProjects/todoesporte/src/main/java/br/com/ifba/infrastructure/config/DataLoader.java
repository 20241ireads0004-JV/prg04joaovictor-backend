package br.com.ifba.infrastructure.config;

import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.esporte.repository.EsporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final EsporteRepository esporteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se a tabela de esportes está vazia
        if (esporteRepository.count() == 0) {
            System.out.println("[DATA LOADER] Inserindo esportes padrão na base de dados...");

            List<Esporte> esportesIniciais = List.of(
                    criarEsporte("Futsal"),
                    criarEsporte("Futebol"),
                    criarEsporte("Vôlei"),
                    criarEsporte("Basquete"),
                    criarEsporte("Handebol")
            );

            // Guarda todos os esportes no banco
            esporteRepository.saveAll(esportesIniciais);
            System.out.println("[DATA LOADER] Esportes cadastrados com sucesso!");
        }
    }

    private Esporte criarEsporte(String nome) {
        Esporte esporte = new Esporte();
        esporte.setNome(nome);
        return esporte;
    }
}