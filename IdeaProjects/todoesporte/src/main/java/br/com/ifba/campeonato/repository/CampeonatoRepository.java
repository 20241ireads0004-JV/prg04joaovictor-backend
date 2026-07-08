package br.com.ifba.campeonato.repository;

import br.com.ifba.campeonato.entity.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampeonatoRepository
        extends JpaRepository<Campeonato, Long> {

    Optional<Campeonato> findByNome(String nome);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(
            String nome,
            Long id
    );

}