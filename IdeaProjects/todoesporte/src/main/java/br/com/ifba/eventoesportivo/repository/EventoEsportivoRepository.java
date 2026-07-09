package br.com.ifba.eventoesportivo.repository;

import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoEsportivoRepository
        extends JpaRepository<EventoEsportivo, Long> {

}