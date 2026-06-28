package br.com.ifba.esporte.repository;

import br.com.ifba.esporte.entity.Esporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EsporteRepository
        extends JpaRepository<Esporte, Long> {

    Optional<Esporte> findByNome(String nome);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(
            String nome,
            Long id
    );

}