package br.com.ifba.classificacao.entity;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

}
