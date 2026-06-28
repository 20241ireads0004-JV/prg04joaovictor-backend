package br.com.ifba.grupoesportivo.repository;

import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoEsportivoRepository
        extends JpaRepository<GrupoEsportivo, Long> {

    Optional<GrupoEsportivo> findByNome(String nome);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(
            String nome,
            Long id
    );

}