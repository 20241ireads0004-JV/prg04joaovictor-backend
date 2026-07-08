package br.com.ifba.campeonato.service;

import br.com.ifba.campeonato.entity.Campeonato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampeonatoIService {

    /*
     * Salva um novo campeonato no banco de dados.
     *
     * Este método realiza validação de
     * unicidade do nome antes de persistir.
     */
    Campeonato save(Campeonato campeonato);

    /*
     * Retorna uma lista com todos os
     * campeonatos cadastrados usando paginação.
     */
    Page<Campeonato> findAll(Pageable pageable);

    /*
     * Retorna o campeonato que contém
     * o ID informado.
     */
    Campeonato findById(Long id);

    /*
     * Remove um campeonato do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de um campeonato.
     */
    Campeonato update(Long id, Campeonato campeonato);

}