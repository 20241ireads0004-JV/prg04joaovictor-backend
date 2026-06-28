package br.com.ifba.avaliacao.service;

import br.com.ifba.avaliacao.entity.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvaliacaoIService {

    /*
     * Salva uma nova avaliação no banco de dados.
     */
    Avaliacao save(Avaliacao avaliacao);

    /*
     * Retorna uma lista com todas
     * as avaliações cadastradas usando paginação.
     */
    Page<Avaliacao> findAll(Pageable pageable);

    /*
     * Retorna a avaliação que contém
     * o ID informado.
     */
    Avaliacao findById(Long id);

    /*
     * Remove uma avaliação do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de uma avaliação.
     */
    Avaliacao update(Long id, Avaliacao avaliacao);

}