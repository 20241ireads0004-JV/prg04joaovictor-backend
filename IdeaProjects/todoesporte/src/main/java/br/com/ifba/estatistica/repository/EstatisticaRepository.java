package br.com.ifba.estatistica.repository;

import br.com.ifba.estatistica.entity.Estatistica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstatisticaRepository
        extends JpaRepository<Estatistica, Long> {
}