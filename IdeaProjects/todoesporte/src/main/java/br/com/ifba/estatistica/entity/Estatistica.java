package br.com.ifba.estatistica.entity;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "estatisticas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Estatistica extends PersistenceEntity implements Serializable {

    @Column(name = "gols", nullable = false)
    private Integer gols;

    @Column(name = "assistencias", nullable = false)
    private Integer assistencias;

    @Column(name = "vitorias", nullable = false)
    private Integer vitorias;

    @Column(name = "derrotas", nullable = false)
    private Integer derrotas;

    @Column(name = "empates", nullable = false)
    private Integer empates;

    @Column(name = "pontos", nullable = false)
    private Integer pontos;

    @OneToOne(mappedBy = "estatistica")
    private Atleta atleta;

}