package br.com.ifba.partida.repository;

import br.com.ifba.partida.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository
        extends JpaRepository<Partida, Long> {

}