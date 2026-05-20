package main.java.br.com.ifba.br.usuario.repository;

import main.java.br.com.ifba.br.usuario.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByLogin(String login);

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