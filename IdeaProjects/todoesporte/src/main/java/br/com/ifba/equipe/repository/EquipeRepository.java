package br.com.ifba.equipe.repository;

import br.com.ifba.equipe.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipeRepository
        extends JpaRepository<Equipe, Long> {

    Optional<Equipe> findByNome(String nome);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(
            String nome,
            Long id
    );
}