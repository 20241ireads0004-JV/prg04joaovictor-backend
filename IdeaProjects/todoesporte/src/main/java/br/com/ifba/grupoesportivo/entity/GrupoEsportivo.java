package br.com.ifba.grupoesportivo.entity;

import br.com.ifba.administrador.entity.Administrador;
import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.infrastructure.entity.PersistenceEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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

    // Relacionamento com o Administrador do Grupo
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    // Muitos grupos -> Um esporte
    @ManyToOne
    @JoinColumn(name = "esporte_id")
    private Esporte esporte;

    // Grupo -> Eventos
    @OneToMany(mappedBy = "grupoEsportivo")
    private List<EventoEsportivo> eventos;

    // Relacionamento de Atletas membros do grupo
    @ManyToMany
    @JoinTable(
            name = "grupo_atletas_membros",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "atleta_id")
    )
    private List<Atleta> atletas = new ArrayList<>();

    // Relacionamento de Atletas que solicitaram entrada
    @ManyToMany
    @JoinTable(
            name = "grupo_atletas_solicitacoes",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "atleta_id")
    )
    private List<Atleta> solicitacoesPendentes = new ArrayList<>();
}