package br.com.ifba.avaliacao.entity;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Avaliacao extends PersistenceEntity implements Serializable {

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "comentario", nullable = false, length = 500)
    private String comentario;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Atleta autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliado_id", nullable = false)
    private Atleta avaliado;

}