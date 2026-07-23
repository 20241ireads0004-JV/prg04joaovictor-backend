package br.com.ifba.infrastructure.config;

import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.esporte.repository.EsporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe responsável por popular a base de dados com esportes padrão
 * assim que a aplicação Spring Boot é inicializada.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final EsporteRepository esporteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se a tabela de esportes já possui registros
        if (esporteRepository.count() == 0) {
            System.out.println("[DATA LOADER] Tabela vazia! Inserindo esportes padrão com todas as colunas obrigatórias...");

            // Criamos a lista preenchendo: Nome, Descrição e Quantidade de Jogadores
            List<Esporte> esportesIniciais = List.of(
                    criarEsporte("Futsal", "Modalidade de futebol praticada em quadra coberta.", 10),
                    criarEsporte("Futebol", "Esporte de equipe jogado em campo gramado.", 22),
                    criarEsporte("Vôlei", "Esporte jogado com bola e rede dividindo a quadra.", 12),
                    criarEsporte("Basquete", "Esporte coletivo cujo objetivo é marcar pontos na cesta.", 10),
                    criarEsporte("Handebol", "Esporte coletivo onde a bola é trabalhada com as mãos.", 14)
            );

            // Persiste todos os esportes na base de dados PostgreSQL
            esporteRepository.saveAll(esportesIniciais);
            System.out.println("[DATA LOADER] Esportes cadastrados com sucesso na base de dados!");
        } else {
            System.out.println("[DATA LOADER] A base de dados já contém esportes cadastrados.");
        }
    }

    /**
     * Método auxiliar para instanciar a entidade Esporte preenchendo todos os campos obrigatórios.
     *
     * @param nome Nome do esporte (único e não nulo)
     * @param descricao Breve descrição (não nulo)
     * @param quantidadeJogadores Número de jogadores por equipe (não nulo)
     * @return Instância de Esporte pronta para persistência
     */
    private Esporte criarEsporte(String nome, String descricao, Integer quantidadeJogadores) {
        Esporte esporte = new Esporte();
        esporte.setNome(nome);
        esporte.setDescricao(descricao);
        esporte.setQuantidadeJogadores(quantidadeJogadores);
        return esporte;
    }
}