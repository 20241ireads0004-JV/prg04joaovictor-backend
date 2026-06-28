package br.com.ifba.estatistica.service;

import br.com.ifba.estatistica.entity.Estatistica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstatisticaIService {

    /*
     * Salva uma nova estatística no banco de dados.
     */
    Estatistica save(Estatistica estatistica);

    /*
     * Retorna uma lista com todas
     * as estatísticas cadastradas usando paginação.
     */
    Page<Estatistica> findAll(Pageable pageable);

    /*
     * Retorna a estatística que contém
     * o ID informado.
     */
    Estatistica findById(Long id);

    /*
     * Remove uma estatística do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de uma estatística.
     */
    Estatistica update(Long id, Estatistica estatistica);

}