package br.com.ifba.classificacao.entity;
import br.com.ifba.campeonato.entity.Campeonato;
import br.com.ifba.equipe.entity.Equipe;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
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
@Table(name = "classificacoes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Classificacao extends PersistenceEntity implements Serializable {

    @Column(name = "posicao", nullable = false)
    private Integer posicao;

    @Column(name = "pontuacao", nullable = false)
    private Integer pontuacao;

    @Column(name = "gols_marcados", nullable = false)
    private Integer golsMarcados;

    @Column(name = "gols_sofridos", nullable = false)
    private Integer golsSofridos;

    // várias classificações -> um campeonato
    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;

    // várias classificações -> uma equipe
    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

}
