package br.com.ifba.local.repository;

import br.com.ifba.local.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalRepository
        extends JpaRepository<Local, Long> {

    Optional<Local> findByNome(String nome);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(
            String nome,
            Long id
    );

}