package br.com.ifba.sorteio.repository;

import br.com.ifba.sorteio.entity.Sorteio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SorteioRepository
        extends JpaRepository<Sorteio, Long> {

}