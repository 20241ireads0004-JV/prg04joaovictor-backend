package br.com.ifba.grupoesportivo.entity;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.campeonato.entity.Campeonato;
import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "grupos_esportivos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GrupoEsportivo extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    // muitos grupos -> um esporte
    @ManyToOne
    @JoinColumn(name = "esporte_id")
    private Esporte esporte;

    // grupo -> eventos
    @OneToMany(mappedBy = "grupoEsportivo")
    private List<EventoEsportivo> eventos;

    // grupo -> campeonatos
    @OneToMany(mappedBy = "grupoEsportivo")
    private List<Campeonato> campeonatos;

    // grupo <-> atletas
    @ManyToMany
    @JoinTable(
            name = "grupo_atleta",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "atleta_id")
    )
    private List<Atleta> atletas;

}