package br.com.ifba.administrador.repository;

import br.com.ifba.administrador.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Insere diretamente o ID do usuário já existente na tabela de administradores,
     * promovendo o usuário a Administrador sem recriar o registro na tabela usuarios.
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO administradores (id) VALUES (:usuarioId) ON CONFLICT (id) DO NOTHING", nativeQuery = true)
    void promoverUsuarioParaAdministrador(@Param("usuarioId") Long usuarioId);

}