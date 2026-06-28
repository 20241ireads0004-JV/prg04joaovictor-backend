package br.com.ifba.atleta.service;

import br.com.ifba.atleta.entity.Atleta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtletaIService {

    /*
     * Salva um novo atleta no banco de dados.
     *
     * Este método realiza validações de
     * unicidade de email e login antes
     * de persistir o atleta.
     */
    Atleta save(Atleta atleta);

    /*
     * Retorna uma lista com todos
     * os atletas cadastrados usando paginação.
     */
    Page<Atleta> findAll(Pageable pageable);

    /*
     * Retorna o atleta que contém
     * o ID informado.
     */
    Atleta findById(Long id);

    /*
     * Remove um atleta do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de um atleta.
     */
    Atleta update(Long id, Atleta atleta);

}