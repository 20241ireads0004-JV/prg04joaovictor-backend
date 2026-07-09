package br.com.ifba.classificacao.service;

import br.com.ifba.classificacao.entity.Classificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassificacaoIService {

    /*
     * Salva uma nova classificação.
     */
    Classificacao save(Classificacao classificacao);

    /*
     * Retorna todas as classificações
     * utilizando paginação.
     */
    Page<Classificacao> findAll(Pageable pageable);

    /*
     * Busca uma classificação pelo ID.
     */
    Classificacao findById(Long id);

    /*
     * Remove uma classificação.
     */
    void delete(Long id);

    /*
     * Atualiza uma classificação.
     */
    Classificacao update(Long id, Classificacao classificacao);

}