package br.com.ifba.esporte.service;

import br.com.ifba.esporte.entity.Esporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EsporteIService {

    /*
     * Salva um novo esporte no banco de dados.
     *
     * Este método realiza a validação de
     * unicidade do nome antes de persistir.
     */
    Esporte save(Esporte esporte);

    /*
     * Retorna uma lista com todos
     * os esportes cadastrados usando paginação.
     */
    Page<Esporte> findAll(Pageable pageable);

    /*
     * Retorna o esporte que contém
     * o ID informado.
     */
    Esporte findById(Long id);

    /*
     * Remove um esporte do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de um esporte.
     *
     * O método verifica:
     * - se o esporte existe;
     * - duplicidade do nome.
     */
    Esporte update(Long id, Esporte esporte);

}