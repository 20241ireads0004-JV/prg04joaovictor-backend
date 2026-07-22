package br.com.ifba.infrastructure.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // IMPORTANTE: Deve ser jakarta.persistence.Id e NAO org.springframework.data.annotation.Id
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

/**
 * Classe superclasse abstrata para persistencia.
 * Fornece a chave primaria (id) para todas as entidades do sistema (Usuario, Atleta, Administrador, etc.).
 */
@MappedSuperclass // Indica ao JPA que os campos desta classe devem ser herdados pelas tabelas filhas
@Data
public abstract class PersistenceEntity implements Serializable {

    /**
     * Chave primaria herdada por todas as entidades.
     * GenerationType.IDENTITY garante a criacao de colunas auto-incremento (SERIAL) no PostgreSQL.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}