package br.com.ifba.partida.service;

import br.com.ifba.partida.entity.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartidaIService {

    /*
     * Salva uma nova partida no banco de dados.
     */
    Partida save(Partida partida);

    /*
     * Retorna uma lista com todas as partidas
     * cadastradas utilizando paginação.
     */
    Page<Partida> findAll(Pageable pageable);

    /*
     * Retorna a partida que contém
     * o ID informado.
     */
    Partida findById(Long id);

    /*
     * Remove uma partida através do ID.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de uma partida.
     */
    Partida update(Long id, Partida partida);

}