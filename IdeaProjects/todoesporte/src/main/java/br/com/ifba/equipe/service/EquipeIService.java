package br.com.ifba.equipe.service;

import br.com.ifba.equipe.entity.Equipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipeIService {

    /*
     * Salva uma nova equipe no banco de dados.
     *
     * Este método realiza a validação de
     * unicidade do nome antes de persistir.
     */
    Equipe save(Equipe equipe);

    /*
     * Retorna uma lista com todas
     * as equipes cadastradas usando paginação.
     */
    Page<Equipe> findAll(Pageable pageable);

    /*
     * Retorna a equipe que contém
     * o ID informado.
     */
    Equipe findById(Long id);

    /*
     * Remove uma equipe do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de uma equipe.
     *
     * O método verifica:
     * - se a equipe existe;
     * - duplicidade do nome.
     */
    Equipe update(Long id, Equipe equipe);

}