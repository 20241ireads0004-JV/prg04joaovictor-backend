package br.com.ifba.atleta.entity;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.equipe.entity.Equipe;
import br.com.ifba.estatistica.entity.Estatistica;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.usuario.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "atletas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Atleta extends Usuario {

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "estatistica_id")
    private Estatistica estatistica;

    @ManyToMany
    @JoinTable(
            name = "atletas_equipes",
            joinColumns = @JoinColumn(name = "atleta_id"),
            inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private List<Equipe> equipes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "atletas_grupos",
            joinColumns = @JoinColumn(name = "atleta_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private List<GrupoEsportivo> gruposEsportivos = new ArrayList<>();

    @OneToMany(
            mappedBy = "autor",
            cascade = CascadeType.ALL
    )
    private List<Avaliacao> avaliacoesRealizadas = new ArrayList<>();

    @OneToMany(
            mappedBy = "avaliado",
            cascade = CascadeType.ALL
    )
    private List<Avaliacao> avaliacoesRecebidas = new ArrayList<>();

}