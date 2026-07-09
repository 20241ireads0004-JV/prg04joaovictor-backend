package br.com.ifba.sorteio.service;

import br.com.ifba.sorteio.entity.Sorteio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SorteioIService {

    /*
     * Salva um novo sorteio no banco de dados.
     */
    Sorteio save(Sorteio sorteio);

    /*
     * Retorna uma lista com todos os sorteios
     * cadastrados utilizando paginação.
     */
    Page<Sorteio> findAll(Pageable pageable);

    /*
     * Retorna o sorteio que contém o ID informado.
     */
    Sorteio findById(Long id);

    /*
     * Remove um sorteio através do ID.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de um sorteio.
     */
    Sorteio update(Long id, Sorteio sorteio);

}