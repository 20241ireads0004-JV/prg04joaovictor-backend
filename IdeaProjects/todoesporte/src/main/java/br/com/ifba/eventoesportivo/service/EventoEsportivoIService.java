package br.com.ifba.eventoesportivo.service;

import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventoEsportivoIService {

    /*
     * Salva um novo evento esportivo.
     */
    EventoEsportivo save(EventoEsportivo eventoEsportivo);

    /*
     * Retorna todos os eventos esportivos
     * utilizando paginação.
     */
    Page<EventoEsportivo> findAll(Pageable pageable);

    /*
     * Busca um evento esportivo pelo ID.
     */
    EventoEsportivo findById(Long id);

    /*
     * Remove um evento esportivo.
     */
    void delete(Long id);

    /*
     * Atualiza um evento esportivo.
     */
    EventoEsportivo update(Long id, EventoEsportivo eventoEsportivo);

}