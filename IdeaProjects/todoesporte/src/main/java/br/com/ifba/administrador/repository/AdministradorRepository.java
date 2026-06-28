package br.com.ifba.administrador.repository;

import br.com.ifba.administrador.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository
        extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByEmail(String email);

    Optional<Administrador> findByLogin(String login);

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