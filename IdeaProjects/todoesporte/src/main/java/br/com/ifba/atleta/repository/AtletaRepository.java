package br.com.ifba.atleta.repository;

import br.com.ifba.atleta.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtletaRepository
        extends JpaRepository<Atleta, Long> {

    Optional<Atleta> findByEmail(String email);

    Optional<Atleta> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmailAndIdNot(
            String email,
            Long id
    );

    boolean existsByLoginAndIdNot(
            String login,
            Long id
    );

}